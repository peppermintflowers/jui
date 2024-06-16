package com.peppermintflowers.poc.dashboard.controller;

import com.peppermintflowers.poc.dashboard.models.Product;
import com.peppermintflowers.poc.dashboard.dto.ProductsFiltersDTO;
import com.peppermintflowers.poc.dashboard.service.DashboardService;

import com.peppermintflowers.poc.dashboard.service.TokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.peppermintflowers.poc.dashboard.models.Cart;

import java.util.List;

@RequestMapping("api/v1/dashboard")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    private DashboardService dashboardService;
    private TokenHandler tokenHandler;

    @Autowired
    public DashboardController(DashboardService dashboardService, TokenHandler tokenHandler){
        this.dashboardService = dashboardService;
        this.tokenHandler = tokenHandler;
    }

        @GetMapping("/view")
        public ResponseEntity<Object> viewProducts(@ParameterObject ProductsFiltersDTO productFilters){
            log.info("productFilters is {}",productFilters.toString());
            List<Product>  products = dashboardService.getProducts(productFilters);
            if(products == null){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(products);
        }

        @GetMapping("/view/product/{productId}")
        public ResponseEntity<Product> viewProduct(@PathVariable Long productId){
            Product product = dashboardService.getProductByProductId(productId);
            return ResponseEntity.ok(product);
        }

        @PostMapping("/cart/update/productId/{productId}/skuId/{skuId}/quantity/{quantity}")
        public ResponseEntity<?> addToCart(@PathVariable Long productId, @PathVariable Long skuId, @PathVariable int quantity, @RequestHeader final HttpHeaders headers){
            Product product = dashboardService.getProductByProductId(productId);
            log.info("Product id is {} ", product.getId());
            String authHeader = headers.getFirst("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return new ResponseEntity<>("Jwt token not found", HttpStatus.UNAUTHORIZED);
            }
            String jwt = authHeader.substring(7);
            String username = tokenHandler.extractUsername(jwt);
            Cart updatedCart = dashboardService.updateCart(username,productId,skuId,quantity,product);
            if (updatedCart==null)
                    return new ResponseEntity<>("Item to be removed not found in cart", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("Cart updated", HttpStatus.OK);
        }

    @GetMapping("/cart")
    public ResponseEntity<Cart> viewCart(@RequestHeader final HttpHeaders headers){
        String authHeader = headers.getFirst("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body(null);
        }
        String jwt = authHeader.substring(7);
        String username = tokenHandler.extractUsername(jwt);
            Cart userCart = dashboardService.getCart(username);
            return ResponseEntity.status(HttpStatus.OK).body(userCart);
    }

}
