package com.yelpclone.service.impl;

import com.yelpclone.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        
        String resetLink = frontendUrl + "/reset-password?token=" + token;
        String emailBody = "You have requested to reset your password. " +
                "Click the link below to reset your password: \n\n" +
                resetLink + "\n\n" +
                "If you did not request a password reset, please ignore this email. " +
                "This link will expire in 24 hours.";
        
        message.setText(emailBody);
        mailSender.send(message);
    }
} 