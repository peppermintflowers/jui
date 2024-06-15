package com.peppermintflowers.poc.dashboard.repository;

import com.peppermintflowers.poc.dashboard.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    public Cart getCartByUsername(String username);
}
