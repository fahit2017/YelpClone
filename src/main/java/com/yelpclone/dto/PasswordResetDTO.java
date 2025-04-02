package com.yelpclone.dto;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String token;
    private String newPassword;
} 