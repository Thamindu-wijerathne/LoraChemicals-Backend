// Purpose: Handles HTTP requests from the frontend.

package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.LoginRequestDTO;
import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lorachemicals.Backend.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user,HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        return userService.addUser(user);
    }

    @GetMapping("all-users")
    public List<User> getUsers(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        return userService.getAllUsers();
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUser(@RequestParam String email,HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");

        User user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User Not Found");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        User user = userService.Login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            UserResponseDTO response = new UserResponseDTO(
                    user.getId(), user.getName(), user.getEmail(), user.getRole()
            );
            // Generate JWT
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole());


            // Return both user info and token
            Map<String, Object> result = new HashMap<>();
            result.put("user", response);
            result.put("token", token);

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/add-users")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");

        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser , HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");

        User user = userService.updateUser(id, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {

        AccessControlUtil.checkAccess(request, "admin");

        boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
    }

}