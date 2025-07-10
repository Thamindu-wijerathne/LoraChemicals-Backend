package com.lorachemicals.Backend.repository;


import com.lorachemicals.Backend.model.CustomerBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerBillRepository extends JpaRepository<CustomerBill, Long> {
}
