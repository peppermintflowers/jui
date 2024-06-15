package com.peppermintflowers.poc.dashboard.service;

public interface TokenHandler {
    String extractUsername(String jwt);
}
