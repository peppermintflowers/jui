package com.peppermint.poc.gateway.model;

public class ConnValidationResponse {
 private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;

    public String getUsername(){
        return username;
    }
    public String getToken(){
        return token;
    }
    public String getMethodType(){
        return methodType;
    }
    public boolean getIsAuthenticated(){
        return isAuthenticated;
    }
    public String getStatus(){
        return status;
    }
}
