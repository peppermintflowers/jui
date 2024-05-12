package com.peppermintflowers.poc.user_management.token;

public class ResponseToken {
    private String jwt;

    private long expiresIn;

    public String getToken() {
        return jwt;
    }

    public ResponseToken setToken(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public ResponseToken setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
