package com.yelpclone.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String token);
} 