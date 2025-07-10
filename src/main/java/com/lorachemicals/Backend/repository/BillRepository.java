package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Long> {
}
