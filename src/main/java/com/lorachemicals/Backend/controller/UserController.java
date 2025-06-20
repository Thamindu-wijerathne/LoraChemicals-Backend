// Purpose: Handles HTTP requests from the frontend.

package com.lorachemicals.Backend.controller;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.lorachemicals.Backend.dto.LoginRequestDTO;
=======
import com.lorachemicals.Backend.dto.UserRequestDTO;
>>>>>>> Stashed changes
=======
import com.lorachemicals.Backend.dto.UserRequestDTO;
>>>>>>> Stashed changes
import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lorachemicals.Backend.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lorachemicals.Backend.model.SalesRep;
>>>>>>> Stashed changes

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

<<<<<<< Updated upstream
    public UserController(UserService userService) {
=======
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper;

    public UserController(UserService userService, SalesrepService salesrepService,
                          WarehouseManagerService warehouseManagerService,
                          SalesRepRepository salesRepRepo,
                          WarehouseManagerRepository warehouseManagerRepo,
                          CustomerService customerService) {
>>>>>>> Stashed changes
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("all-users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUser(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User Not Found");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
=======
=======
>>>>>>> Stashed changes
    public ResponseEntity<?> addUser(@RequestBody UserRequestDTO userDTO, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin", "salesrep");

            User user = modelMapper.map(userDTO, User.class);

            User savedUser = userService.addUser(user);
            logger.info("Saved user: {}", savedUser);


            if ("salesrep".equalsIgnoreCase(savedUser.getRole())) {
                SalesRep salesRep = new SalesRep();
                salesRep.setUser(savedUser);
                salesrepService.saveSalesRep(salesRep);
            } else if ("warehouse".equalsIgnoreCase(savedUser.getRole())) {
                WarehouseManager warehouseManager = new WarehouseManager();
                warehouseManager.setUser(savedUser);
                warehouseManagerService.saveWarehouseManager(warehouseManager);
            } else if ("customer".equalsIgnoreCase(savedUser.getRole())) {
                Customer newCustomer = new Customer();
                newCustomer.setUser(savedUser);
                newCustomer.setShop_name(userDTO.getShop_name());

                SalesRep relatedRep = salesrepService.findById(userDTO.getSrepid());
//                Route relatedRoute = routeService.getRouteById(userDTO.getRouteid());

                newCustomer.setSalesRep(relatedRep);
//                newCustomer.setRoute(relatedRoute);

                customerService.saveCustomer(newCustomer);
            }

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            logger.error("Error in addUser:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role"); // Or however you're setting it from JWT

        if (!"admin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

}