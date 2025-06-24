package com.lorachemicals.Backend.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

public class AccessControlUtil {

    public static void checkAccess(HttpServletRequest request, String... allowedRoles) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        String role;
        try {
            role = JwtUtil.extractUserRole(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        List<String> roleList = Arrays.asList(allowedRoles);
        if (!roleList.contains(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied for role: " + role);
        }
    }

}
