package com.peppermintflowers.poc.dashboard.service;
import com.peppermintflowers.poc.dashboard.models.Product;

import java.util.List;
import java.util.Optional;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;

public interface DashboardService {
    public List<Product> getAllProducts();

    List<Product> getAllProducts(ProductFilters productFilters);

    Product getProductByProductId(Long productId);
}
