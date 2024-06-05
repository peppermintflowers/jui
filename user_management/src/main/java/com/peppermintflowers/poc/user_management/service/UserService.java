package com.peppermintflowers.poc.user_management.service;

import com.peppermintflowers.poc.user_management.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public Optional<User> registerUser(User user);
    public User getUserByUsernameAndPassword(String username, String password) ;

}
