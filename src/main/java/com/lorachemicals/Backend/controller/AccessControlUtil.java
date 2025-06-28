package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccessControlUtil {

    public static boolean checkAccess(HttpServletRequest request, String... allowedRoles) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String userRole = JwtUtil.getRoleFromToken(token);

        if (userRole == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token or role not found");
        }

        for (String role : allowedRoles) {
            if (role.equalsIgnoreCase(userRole)) {
                return true; // ✅ Access granted
            }
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }
}