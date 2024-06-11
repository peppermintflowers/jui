package com.peppermintflowers.poc.dashboard.repository;

import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long>, FilterRepository {
    public Optional<Product> findProductById(Long id);
    public List<Product> findAll();
}
