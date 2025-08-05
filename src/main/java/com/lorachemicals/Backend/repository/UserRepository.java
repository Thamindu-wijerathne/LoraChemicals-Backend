// Purpose: Connects the application with the database. Uses JPA to access User data.

package com.lorachemicals.Backend.repository;

// repository/UserRepository.java
import com.lorachemicals.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods (optional)
    User findByEmail(String email);
    User findByEmailAndNic(String email, String nic);
    User findByEmailAndPassword(String email, String password);
  
    @Query("SELECT u FROM User u WHERE u.role = 'customer'")
    List<User> findAllCustomers();

    // Count active staff (excluding customers)
    @Query("SELECT COUNT(u) FROM User u WHERE u.role <> 'customer' AND u.status = 'active'")
    long countActiveStaff();
}

