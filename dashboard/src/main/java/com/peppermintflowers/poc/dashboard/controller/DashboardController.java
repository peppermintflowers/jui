package com.peppermintflowers.poc.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/dashboard")
@RestController
public class DashboardController {
        @GetMapping("/products")
        public ResponseEntity<String> welcome() {

            String welcome = "welcome";

            return ResponseEntity.ok(welcome);
        }

        @GetMapping("/cart")
        public ResponseEntity<String> cart(){
            String cart = "This is a logged in user's cart";
            return ResponseEntity.ok(cart);
        }
}
