package com.peppermintflowers.poc.user_management.controller;

import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.service.UserService;
import com.peppermintflowers.poc.user_management.service.TokenHandler;
import com.peppermintflowers.poc.user_management.token.ResponseToken;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
        try{
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> loginUser(@RequestBody User user){
            User userData = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
            String jwt = jwtGenerator.generateToken(userData);
            ResponseToken token = new ResponseToken().setToken(jwt).setExpiresIn(jwtGenerator.getExpiryTime());
            return ResponseEntity.ok(token);

    }

}
