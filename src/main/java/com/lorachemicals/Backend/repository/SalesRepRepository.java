package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRepRepository extends JpaRepository<SalesRep, Long> {
    // Optional: Custom query to find SalesRep by User
    SalesRep findByUserId(Long userId);
    Optional<SalesRep> findByUser_Id(Long userId);

}
