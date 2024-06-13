package com.peppermintflowers.poc.dashboard.service;
import com.peppermintflowers.poc.dashboard.models.Cart;
import com.peppermintflowers.poc.dashboard.models.Product;

import java.util.List;
import java.util.Optional;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;

public interface DashboardService {
    List<Product> getAllProducts(ProductFilters productFilters);

    Product getProductByProductId(Long productId);

    void persistUpdatedCart(Cart userCart);

    Cart getCart(String username);

    Cart updateCart(String username, Long productId, Long skuId, int quantity, Product product);
}
