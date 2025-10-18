// Purpose: Contains business logic (e.g., how to find a user, create one, etc.).

package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepo, PasswordService passwordService) {
        this.userRepo = userRepo;
        this.passwordService = passwordService;
    }

    public User addUser(User user) {
        // Hash the password before saving
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashedPassword = passwordService.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
        }
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }


    public User findByEmail(String email) { return userRepo.findByEmail(email); }

    public User Login(String email, String password) { 
        User user = userRepo.findByEmail(email);
        if (user != null) {
            // Check if password is already hashed (BCrypt hashes start with $2a$, $2b$, or $2y$)
            if (user.getPassword().startsWith("$2")) {
                // Password is hashed, verify with BCrypt
                if (passwordService.verifyPassword(password, user.getPassword())) {
                    return user;
                }
            } else {
                // Password is plain text (legacy), do direct comparison
                if (password.equals(user.getPassword())) {
                    // For security, hash the password now and update the user
                    String hashedPassword = passwordService.hashPassword(password);
                    user.setPassword(hashedPassword);
                    userRepo.save(user);
                    return user;
                }
            }
        }
        return null;
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepo.findById(id).map(existingUser -> {
            existingUser.setFname(updatedUser.getFname());
            existingUser.setLname(updatedUser.getLname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setNic(updatedUser.getNic());
            existingUser.setStatus(updatedUser.getStatus());

            // Only update password if it's not null or empty, and hash it
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String hashedPassword = passwordService.hashPassword(updatedUser.getPassword());
                existingUser.setPassword(hashedPassword);
            }
            return userRepo.save(existingUser);
        }).orElse(null);
    }


    public boolean deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> getAllCustomers() {
        return userRepo.findAllCustomers();
    }

    public User findByEmailAndNic(String email, String nic) {
        return userRepo.findByEmailAndNic(email, nic);
    }

    public boolean verifyCurrentPassword(String email, String currentPassword) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return passwordService.verifyPassword(currentPassword, user.getPassword());
        }
        return false;
    }


}