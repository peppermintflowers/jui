package com.peppermintflowers.poc.user_management.controller;

import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/dashboard")
@RestController
public class DashboardController {
        @GetMapping
        public ResponseEntity<User> authenticatedUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User currentUser = (User) authentication.getPrincipal();

            return ResponseEntity.ok(currentUser);
        }
}
