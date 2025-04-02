package com.yelpclone.controller;

import com.yelpclone.dto.AuthRequest;
import com.yelpclone.dto.AuthResponse;
import com.yelpclone.dto.PasswordResetDTO;
import com.yelpclone.dto.PasswordResetRequestDTO;
import com.yelpclone.dto.UserDTO;
import com.yelpclone.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequestDTO request) {
        authService.requestPasswordReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        authService.resetPassword(resetDTO);
        return ResponseEntity.ok().build();
    }
} 