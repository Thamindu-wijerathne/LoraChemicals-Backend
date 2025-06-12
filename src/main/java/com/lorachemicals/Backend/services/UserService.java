// Purpose: Contains business logic (e.g., how to find a user, create one, etc.).

package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User findByEmail(String email) { return userRepo.findByEmail(email); }

    public User Login(String email, String password) { return userRepo.findByEmailAndPassword(email, password); }
}