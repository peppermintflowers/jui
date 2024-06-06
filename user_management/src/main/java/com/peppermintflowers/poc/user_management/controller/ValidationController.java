package com.peppermintflowers.poc.user_management.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.peppermintflowers.poc.user_management.service.TokenHandler;
import com.peppermintflowers.poc.user_management.service.UserService;
import com.peppermintflowers.poc.user_management.token.ResponseToken;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/validateToken")
public class ValidationController {
    private TokenHandler jwtGenerator;

    @Autowired
    public ValidationController(UserService userService, TokenHandler jwtGenerator){
        this.jwtGenerator=jwtGenerator;
    }

     @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseToken> validatePost(HttpServletRequest request) {
        String jwt = (String) request.getAttribute("token");
        ResponseToken token = new ResponseToken();
        token.setToken(jwt);
        token.setExpiresIn(jwtGenerator.getExpiryTime());
        return ResponseEntity.ok(token);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseToken> validateGet(HttpServletRequest request) {
        String jwt = (String) request.getAttribute("token");
        ResponseToken token = new ResponseToken();
        token.setToken(jwt);
        token.setExpiresIn(jwtGenerator.getExpiryTime());
        return ResponseEntity.ok(token);
    }

}
