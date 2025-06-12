package com.lorachemicals.Backend.repository;

// repository/UserRepository.java
import com.lorachemicals.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods (optional)
    User findByEmail(String email);
}
