// Purpose: Handles HTTP requests from the frontend.

package com.lorachemicals.Backend.controller;


import com.lorachemicals.Backend.dto.CustomerUserDTO;
import com.lorachemicals.Backend.dto.UserRequestDTO;
import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.*;
        import com.lorachemicals.Backend.repository.SalesRepRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;
import com.lorachemicals.Backend.services.*;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final SalesrepService salesrepService;
    private final WarehouseManagerService warehouseManagerService;
    private final SalesRepRepository salesRepRepo;
    private final WarehouseManagerRepository warehouseManagerRepo;
    private final CustomerService customerService;
    private final RouteService routeService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ModelMapper modelMapper;
    public UserController(UserService userService, SalesrepService salesrepService,
                          WarehouseManagerService warehouseManagerService,
                          SalesRepRepository salesRepRepo,
                          WarehouseManagerRepository warehouseManagerRepo,
                          CustomerService customerService, RouteService routeService) {
        this.userService = userService;
        this.salesrepService = salesrepService;
        this.warehouseManagerService = warehouseManagerService;
        this.salesRepRepo = salesRepRepo;
        this.warehouseManagerRepo = warehouseManagerRepo;
        this.customerService = customerService; // ✅ FIXED
        this.routeService = routeService;
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

//    @GetMapping("/all-customers")
//    public List<User> getCustomers(HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "salesrep");
//        return userService.getAllCustomers();
//    }

    @GetMapping("/my-customers")
    public List<CustomerUserDTO> getMyCustomers(HttpServletRequest request) {
        logger.info("GET /my-customers called");
        AccessControlUtil.checkAccess(request, "salesrep");

        String authHeader = request.getHeader("Authorization");
        String token = authHeader != null ? authHeader.replace("Bearer ", "") : null;
        Long userId = JwtUtil.extractUserId(token);

        SalesRep salesRep = salesrepService.getSalesRepById(userId);
        if (salesRep == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SalesRep not found");
        }

        List<Customer> customers = customerService.getCustomersBySalesRep(salesRep);

        // Map each customer + user into the combined DTO
        return customers.stream().map(c -> new CustomerUserDTO(
                c.getCustomerid(),
                c.getShopName(),
                c.getSalesRep() != null ? c.getSalesRep().getSrepid() : null,
                c.getRoute() != null ? c.getRoute().getRouteid() : null,
                c.getUser().getId(),
                c.getUser().getFname(),
                c.getUser().getLname(),
                c.getUser().getEmail(),
                c.getUser().getRole(),
                c.getUser().getAddress(),
                c.getUser().getPhone(),
                c.getUser().getNic(),
                c.getUser().getStatus()
        )).toList();
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
                    user.getNic(),
                    user.getStatus()
            );

            // Generate JWT
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());


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
                newCustomer.setShopName(userDTO.getShop_name());

                SalesRep relatedRep = salesrepService.getSalesRepById(userDTO.getSrepid());
                Route relatedRoute = routeService.getRouteById(userDTO.getRouteid());

                newCustomer.setSalesRep(relatedRep);
                newCustomer.setRoute(relatedRoute);

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

    @PutMapping("/update-customer/{userId}")
    public ResponseEntity<?> updateCustomerWithUser(@PathVariable Long userId, @RequestBody CustomerUserDTO dto, HttpServletRequest request) {
        try {
            AccessControlUtil.checkAccess(request, "admin", "salesrep");

            // Update User
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            user.setFname(dto.getFname());
            user.setLname(dto.getLname());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());
            user.setAddress(dto.getAddress());
            user.setNic(dto.getNic());
            user.setRole(dto.getRole());
            userService.updateUser(userId, user);

            // Update Customer
            Customer customer = customerService.getCustomerByUserId(userId);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }

            customer.setShopName(dto.getShop_name());
            logger.error("Updating Shop Name: {}", dto);


            if (dto.getRouteid() != null) {
                Route route = routeService.getRouteById(dto.getRouteid());
                customer.setRoute(route);
            }

            if (dto.getSrepid() != null) {
                SalesRep salesRep = salesrepService.getSalesRepById(dto.getSrepid());
                customer.setSalesRep(salesRep);
            }

            customerService.saveCustomer(customer);

            return ResponseEntity.ok("Customer and User updated successfully.");

        } catch (Exception e) {
            logger.error("Error in Update User:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }




    //hard delete from user table
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        try {
            // Allow both "admin" and "salesrep" roles
            AccessControlUtil.checkAccess(request, "admin", "salesrep");

            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            logger.error("Error in Delete User:", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
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