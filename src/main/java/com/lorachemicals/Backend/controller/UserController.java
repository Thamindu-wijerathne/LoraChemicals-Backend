// Purpose: Handles HTTP requests from the frontend.

package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.UserRequestDTO;
import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.Customer;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.repository.SalesRepRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;
import com.lorachemicals.Backend.services.CustomerService;
import com.lorachemicals.Backend.services.SalesrepService;
import com.lorachemicals.Backend.services.UserService;
import com.lorachemicals.Backend.services.WarehouseManagerService;
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
import com.lorachemicals.Backend.model.SalesRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SalesrepService salesrepService;
    private final WarehouseManagerService warehouseManagerService;
    private final SalesRepRepository salesRepRepo;
    private final WarehouseManagerRepository warehouseManagerRepo;
    private final CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper;


    public UserController(UserService userService, SalesrepService salesrepService,
                          WarehouseManagerService warehouseManagerService,
                          SalesRepRepository salesRepRepo,
                          WarehouseManagerRepository warehouseManagerRepo,
                          CustomerService customerService) {
        this.userService = userService;
        this.salesrepService = salesrepService;
        this.warehouseManagerService = warehouseManagerService;
        this.salesRepRepo = salesRepRepo;
        this.warehouseManagerRepo = warehouseManagerRepo;
        this.customerService = customerService; // ✅ FIXED
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

    @GetMapping("/all-customers")
    public List<User> getCustomers(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep");
        return userService.getAllCustomers();
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
    public ResponseEntity<?> loginUser(@RequestBody UserResponseDTO loginRequest) {
        User user = userService.Login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            UserResponseDTO response = new UserResponseDTO(
                    user.getId(),
                    user.getFname(),
                    user.getLname(),
                    user.getEmail(),
                    user.getRole(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getNic()
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
    public ResponseEntity<?> addUser(@RequestBody UserRequestDTO userDTO, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin", "salesrep");

            User user = modelMapper.map(userDTO, User.class);

            User savedUser = userService.addUser(user);

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

                SalesRep relatedRep = salesrepService.getSalesRepById(userDTO.getSrepid());
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
    }



    // File: UserController.java

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "salesrep");

        // Get the current user
        User savedUser = userService.updateUser(id, updatedUser);
        if (savedUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Determine old role from related tables
        String oldRole = "";
        if (salesRepRepo.findByUser_Id(id).isPresent()) {
            oldRole = "salesrep";
        } else if (warehouseManagerRepo.findByUser_Id(id).isPresent()) {
            oldRole = "warehouse";
        }

        String newRole = updatedUser.getRole();

        // If role changed, delete old and insert new
        if (!newRole.equalsIgnoreCase(oldRole)) {
            if ("salesrep".equalsIgnoreCase(oldRole)) {
                salesrepService.deleteByUserId(id);
            } else if ("warehouse".equalsIgnoreCase(oldRole)) {
                warehouseManagerService.deleteByUserId(id);
            }

            if ("salesrep".equalsIgnoreCase(newRole)) {
                SalesRep sr = new SalesRep();
                sr.setUser(savedUser);
                salesrepService.saveSalesRep(sr);
            } else if ("warehouse".equalsIgnoreCase(newRole)) {
                WarehouseManager wm = new WarehouseManager();
                wm.setUser(savedUser);
                warehouseManagerService.saveWarehouseManager(wm);
            }
        }

        return ResponseEntity.ok(savedUser);
    }



    //hard delete from user table
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // Allow both "admin" and "salesrep" roles
        AccessControlUtil.checkAccess(request, "admin", "salesrep");

        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    //view my profile
    @GetMapping("/viewaccount/{id}")
    public ResponseEntity<?> ViewAccount(@PathVariable Long id, HttpServletRequest request) {

        AccessControlUtil.checkAccess(request, "admin", "salesrep" , "customer" , "warehouse");

        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User Not Found");
        }
    }

    //update my profile
    @PutMapping("updateaccount/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User updatedUser, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "salesrep" , "customer" , "warehouse");

        // Get the current user
        User savedUser = userService.updateUser(id, updatedUser);
        if (savedUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(savedUser);
    }


}