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

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }


    public User findByEmail(String email) { return userRepo.findByEmail(email); }

    public User Login(String email, String password) { return userRepo.findByEmailAndPassword(email, password); }

    public User updateUser(Long id, User updatedUser) {
        return userRepo.findById(id).map(existingUser -> {
            existingUser.setFname(updatedUser.getFname());
            existingUser.setLname(updatedUser.getLname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setNic(updatedUser.getNic());

            // Only update password if it's not null or empty
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(updatedUser.getPassword());
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



}