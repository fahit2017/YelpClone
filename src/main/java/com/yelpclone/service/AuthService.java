package com.yelpclone.service;

import com.yelpclone.dto.AuthRequest;
import com.yelpclone.dto.AuthResponse;
import com.yelpclone.dto.PasswordResetDTO;
import com.yelpclone.dto.PasswordResetRequestDTO;
import com.yelpclone.dto.UserDTO;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserDTO register(UserDTO userDTO);
    void requestPasswordReset(PasswordResetRequestDTO request);
    void resetPassword(PasswordResetDTO resetDTO);
} 