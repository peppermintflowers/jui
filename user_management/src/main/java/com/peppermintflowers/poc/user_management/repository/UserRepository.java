package com.peppermintflowers.poc.user_management.repository;

import com.peppermintflowers.poc.user_management.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findUserByUsername(String username);
}
