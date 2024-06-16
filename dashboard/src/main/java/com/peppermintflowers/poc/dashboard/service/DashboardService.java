package com.peppermintflowers.poc.dashboard.service;
import com.peppermintflowers.poc.dashboard.models.Cart;
import com.peppermintflowers.poc.dashboard.models.Product;

import java.util.List;

import com.peppermintflowers.poc.dashboard.dto.ProductsFiltersDTO;

public interface DashboardService {
    List<Product> getProducts(ProductsFiltersDTO productFilters);

    Product getProductByProductId(Long productId);

    void persistUpdatedCart(Cart userCart);

    Cart getCart(String username);

    Cart updateCart(String username, Long productId, Long skuId, int quantity, Product product);
}
