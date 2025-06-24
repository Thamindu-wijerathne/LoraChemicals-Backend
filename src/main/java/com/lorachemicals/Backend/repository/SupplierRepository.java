// Purpose: Connects the application with the Supplier table using JPA.

package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // You can add custom queries here if needed
}

