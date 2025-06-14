// src/main/java/com/lorachemicals/Backend/util/JwtUtil.java

package com.lorachemicals.Backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "your-256-bit-secret-key-should-be-32-bytes!";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(KEY, SignatureAlgorithm.HS256)  // NEW preferred method
                .compact();
    }
}
