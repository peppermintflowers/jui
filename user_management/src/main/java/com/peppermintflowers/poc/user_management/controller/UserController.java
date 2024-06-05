package com.peppermintflowers.poc.user_management.controller;

import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.service.UserService;
import com.peppermintflowers.poc.user_management.service.TokenHandler;
import com.peppermintflowers.poc.user_management.token.ResponseToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class UserController {

    private UserService userService;
    private TokenHandler jwtGenerator;

    @Autowired
    public UserController(UserService userService, TokenHandler jwtGenerator){
        this.userService=userService;
        this.jwtGenerator=jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody User user){

           Optional<User> optionalUser =userService.registerUser(user);
        return optionalUser.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>("Unable to create user", HttpStatus.CONFLICT));

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> loginUser(@RequestBody User user){
            User userData = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
            String jwt = jwtGenerator.generateToken(userData);
            ResponseToken token = new ResponseToken();
            token.setToken(jwt);
            token.setExpiresIn(jwtGenerator.getExpiryTime());
            return ResponseEntity.ok(token);

    }

}
