package com.yelpclone.service.impl;

import com.yelpclone.dto.AuthRequest;
import com.yelpclone.dto.AuthResponse;
import com.yelpclone.dto.PasswordResetDTO;
import com.yelpclone.dto.PasswordResetRequestDTO;
import com.yelpclone.dto.UserDTO;
import com.yelpclone.model.PasswordResetToken;
import com.yelpclone.model.User;
import com.yelpclone.repository.PasswordResetTokenRepository;
import com.yelpclone.repository.UserRepository;
import com.yelpclone.security.JwtUtils;
import com.yelpclone.service.AuthService;
import com.yelpclone.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtils jwtUtils,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PasswordResetTokenRepository passwordResetTokenRepository,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtils.generateToken(userDetails);

        return new AuthResponse(token, userDetails.getUsername());
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);
        UserDTO response = new UserDTO();
        BeanUtils.copyProperties(savedUser, response);
        response.setPassword(null); // Don't send password back

        return response;
    }

    @Override
    public void requestPasswordReset(PasswordResetRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        // Generate a unique token
        String token = UUID.randomUUID().toString();
        
        // Create a new password reset token
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token expires in 24 hours
        
        passwordResetTokenRepository.save(resetToken);
        
        // Send email with reset link
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    @Override
    public void resetPassword(PasswordResetDTO resetDTO) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenAndUsedFalse(resetDTO.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired password reset token"));
        
        // Check if token is expired
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired");
        }
        
        // Get the user associated with the token
        User user = resetToken.getUser();
        
        // Update the user's password
        user.setPassword(passwordEncoder.encode(resetDTO.getNewPassword()));
        userRepository.save(user);
        
        // Mark the token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }
} 