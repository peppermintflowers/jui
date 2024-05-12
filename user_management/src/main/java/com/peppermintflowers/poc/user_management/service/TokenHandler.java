package com.peppermintflowers.poc.user_management.service;

import com.peppermintflowers.poc.user_management.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenHandler {
    String generateToken(User user);
    long getExpiryTime();

    String extractUsername(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);
}
