package com.peppermint.poc.gateway.model;

import lombok.Getter;

@Getter
public class ResponseToken {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;
    private long expiresIn;
}
