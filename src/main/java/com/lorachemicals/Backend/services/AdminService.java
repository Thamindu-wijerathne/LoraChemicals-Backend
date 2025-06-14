package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.model.Admin;
import com.lorachemicals.Backend.repository.UserRepository;
import com.lorachemicals.Backend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}
