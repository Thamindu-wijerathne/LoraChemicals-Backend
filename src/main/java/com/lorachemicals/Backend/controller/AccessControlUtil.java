package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class AccessControlUtil {

    public static ResponseEntity<String> checkAccess(HttpServletRequest request, String... allowedRoles) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String userRole = JwtUtil.getRoleFromToken(token);

        for (String role : allowedRoles) {
            if (role.equalsIgnoreCase(userRole)) {
                return null; // Access granted
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
}
