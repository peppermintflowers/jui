package com.peppermintflowers.poc.dashboard.repository;

import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;

import java.util.List;

public interface FilterRepository {
    List<Product> findByProductFilters(ProductFilters productFilters);
}
