package com.lorachemicals.Backend.util;

import com.lorachemicals.Backend.services.PasswordService;

/**
 * Utility class to test password hashing functionality
 */
public class PasswordTestUtil {
    
    public static void main(String[] args) {
        PasswordService passwordService = new PasswordService();
        
        // Test password hashing
        String plainPassword = "testPassword123";
        String hashedPassword = passwordService.hashPassword(plainPassword);
        
        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        
        // Test password verification
        boolean isValid = passwordService.verifyPassword(plainPassword, hashedPassword);
        System.out.println("Password verification: " + isValid);
        
        // Test with wrong password
        boolean isInvalid = passwordService.verifyPassword("wrongPassword", hashedPassword);
        System.out.println("Wrong password verification: " + isInvalid);
    }
}