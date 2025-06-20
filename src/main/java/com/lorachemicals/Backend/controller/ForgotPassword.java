// src/main/java/com/lorachemicals/Backend/controller/ForgotPassword.java
package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;


import java.util.Random;


@RestController
@RequestMapping("/forgot")
public class ForgotPassword {
    private static final Logger logger = LoggerFactory.getLogger(ForgotPassword.class);

    private final UserService userService;

    public ForgotPassword(UserService userService) {
        this.userService = userService;
    }

    public String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    private JavaMailSender mailSender;
    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Password Reset");
        message.setText("Your OTP is: " + otp + "\nThis code will expire in 5 minutes.");
        mailSender.send(message);
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserResponseDTO request) {
        User user = userService.findByEmailAndNic(request.getEmail(), request.getNic());

        if (user != null) {

            String otp = generateOtp();
            logger.info("🔐 Generated OTP: {}", otp);

//           sendOtpEmail(request.getEmail(), otp);
            
            logger.info("✅ User found: {}", user); // log with user info
            return ResponseEntity.ok("User found, reset email can be sent.");
        } else {
            logger.warn("❌ No user found for email: {} and NIC: {}", request.getEmail(), request.getNic());
            return ResponseEntity.status(404).body("No user found with matching email and NIC.");
        }
    }




}
