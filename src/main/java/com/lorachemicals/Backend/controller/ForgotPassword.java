// src/main/java/com/lorachemicals/Backend/controller/ForgotPassword.java
package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/forgot")
public class ForgotPassword {
    private static final Logger logger = LoggerFactory.getLogger(ForgotPassword.class);

    private final UserService userService;

    public ForgotPassword(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserResponseDTO request) {
        User user = userService.findByEmailAndNic(request.getEmail(), request.getNic());

        if (user != null) {
            logger.info("✅ User found: {}", user); // log with user info
            return ResponseEntity.ok("User found, reset email can be sent.");
        } else {
            logger.warn("❌ No user found for email: {} and NIC: {}", request.getEmail(), request.getNic());
            return ResponseEntity.status(404).body("No user found with matching email and NIC.");
        }
    }

    

}
