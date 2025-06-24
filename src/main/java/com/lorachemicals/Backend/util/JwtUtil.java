package com.lorachemicals.Backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    // ADD THIS LOGGER
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String SECRET_KEY = "your-256-bit-secret-key-should-be-32-bytes!";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String email, String role, Long id) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("userId", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public static String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateToken(String token, String email) {
        try {
            return extractEmail(token).equals(email) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // FIXED: Use consistent parsing method and KEY
    public static boolean isTokenValid(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Authorization header is null or doesn't start with Bearer");
                return false;
            }

            String token = authHeader.substring(7);

            // Use the same parsing method as getClaims for consistency
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)  // Use the same KEY, not SECRET_KEY string
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Check if token is expired
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                logger.warn("Token is expired");
                return false;
            }

            logger.info("Token is valid");
            return true;
        } catch (Exception e) {
            logger.error("Token validation error: ", e);
            return false;
        }
    }
    // Fixed getRoleFromToken method
    public static String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)  // Use the SecretKey object, not the raw string
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("role", String.class);
        } catch (JwtException e) {
            logger.error("Error extracting role from token: ", e);
            throw new RuntimeException("Invalid token", e);
        }
    }

    public static Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY) // ✅ use the correct key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("userId", Long.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public static String extractUserRole(String token) {
        return getRoleFromToken(token);
    }


}