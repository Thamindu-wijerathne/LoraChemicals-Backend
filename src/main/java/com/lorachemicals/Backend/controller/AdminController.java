package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.services.AdminService;
import com.lorachemicals.Backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User savedUser = adminService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
