package com.peppermintflowers.poc.dashboard.service;

import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;
import com.peppermintflowers.poc.dashboard.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService{
    private final ProductRepository productRepository;
    @Override
    public List<Product> getAllProducts() {
    List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public List<Product> getAllProducts(ProductFilters productFilters) {
        List<Product> products;
        products  = productRepository.findByProductFilters(productFilters);
        return products;
    }

    @Override
    public Product getProductByProductId(Long productId) {
        Optional<Product> product = productRepository.findProductById(productId);
        return product.orElseThrow();
    }
}
