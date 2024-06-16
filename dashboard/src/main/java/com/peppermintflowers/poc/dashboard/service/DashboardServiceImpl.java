package com.peppermintflowers.poc.dashboard.service;

import com.peppermintflowers.poc.dashboard.models.Cart;
import com.peppermintflowers.poc.dashboard.models.CartObject;
import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.dto.ProductsFiltersDTO;
import com.peppermintflowers.poc.dashboard.repository.CartRepository;
import com.peppermintflowers.poc.dashboard.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService{

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    //private final UserRepository userRepository;

    @Cacheable(
            key="#productFilters.toString()",
            value="dashboardProductCache",
            cacheManager = "defaultCacheManager"
    )
    @Override
    public List<Product> getProducts(ProductsFiltersDTO productFilters) {
        if(productFilters.isEmpty())
        {log.info("In the correct step");
            return productRepository.findAll();
        }
        return productRepository.findByProductFilters(productFilters);
    }

    @Override
    public Product getProductByProductId(Long productId) {
        Optional<Product> product = productRepository.findProductById(productId);
        return product.orElseThrow();
    }

    @Override
    public Cart getCart(String username) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User currentUser = (User) authentication.getPrincipal();
        //Optional<User> currentUser = userRepository.findUserByUsername(username);
        //String userId = currentUser.get().getId();
            Cart cart = cartRepository.getCartByUsername(username);
            if(cart == null){
                cart = new Cart(username);
                cartRepository.save(cart);
            }
            return cart;
        }

    @Override
    public Cart updateCart(String username, Long productId, Long skuId, int quantity, Product product) {
        Cart userCart = getCart(username);
        log.info("Cart fetched");
        List<CartObject> cartObjects = userCart.getCartObjects();
        CartObject updatedItem = null;
        if(!cartObjects.isEmpty()) {
            log.info("Checking items");
            for (CartObject cartObject : cartObjects) {
                log.info("productId {}",cartObject.getProductId());
                log.info("skuId {}", cartObject.getSkuId());
                if (cartObject.getProductId().equals(productId) && cartObject.getSkuId().equals(skuId)) {
                    log.info("Object found");
                    updatedItem = cartObject;
                }
            }
        }
        if(updatedItem == null){
            log.info("cart object is not already present");
            if(quantity>0){
                log.info("cart object needs to be added");
                CartObject cartObject = new CartObject(productId, skuId, quantity, product.getPrice(), product.getDefaultImageUrl());
                userCart.addToCart(cartObject);
            }
            else{
                return null;
            }
        }
        else{
            if(quantity == 0){
                userCart.removeFromCart(updatedItem);
            }
            else{
                updatedItem.setQuantity(quantity);
            }

        }
        persistUpdatedCart(userCart);
        log.info("Cart persisted");
        return userCart;
    }

    @Override
    public void persistUpdatedCart(Cart userCart) {

        log.info("persisting cart");
        cartRepository.save(userCart);
    }

}



