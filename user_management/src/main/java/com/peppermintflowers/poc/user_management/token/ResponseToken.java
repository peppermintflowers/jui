package com.peppermintflowers.poc.user_management.token;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseToken {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;
    private long expiresIn;
}
