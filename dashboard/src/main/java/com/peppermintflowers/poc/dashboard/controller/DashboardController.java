package com.peppermintflowers.poc.dashboard.controller;

import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.models.ProductFilters;
import com.peppermintflowers.poc.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/dashboard")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    private final DashboardService dashboardService;
       /* @GetMapping("/products")
        public ResponseEntity<String> welcome() {

            String welcome = "welcome";

            return ResponseEntity.ok(welcome);
        }*/

        @GetMapping("/cart")
        public ResponseEntity<String> cart(){
            String cart = "This is a logged in user's cart";
            return ResponseEntity.ok(cart);
        }

/*
    @GetMapping("/view")
    public ResponseEntity<List<Product>> viewProducts(){
        List<Product> products = dashboardService.getAllProducts();
        return ResponseEntity.ok(products);
    }*/

        @GetMapping("/view")
        public ResponseEntity<List<Product>> viewProducts(@RequestBody(required = false) ProductFilters productFilters){
            List<Product> products;
            if(productFilters==null){
                log.info("No Filters");
                products = dashboardService.getAllProducts();
            }
            else {
                log.info("With Filters");
                products = dashboardService.getAllProducts(productFilters);
            }
            return ResponseEntity.ok(products);
        }

        @GetMapping("/view/product/{productId}")
        public ResponseEntity<Product> viewProduct(@PathVariable Long productId){
            Product product = dashboardService.getProductByProductId(productId);
            return ResponseEntity.ok(product);
        }
}
