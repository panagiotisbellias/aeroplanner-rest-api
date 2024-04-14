package com.projects.aeroplannerrestapi.service;

public interface TokenBlacklistService {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}
