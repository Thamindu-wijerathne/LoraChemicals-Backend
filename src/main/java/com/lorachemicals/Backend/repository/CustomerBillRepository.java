package com.lorachemicals.Backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.CustomerBill;

public interface CustomerBillRepository extends JpaRepository<CustomerBill, Long> {
    CustomerBill findByBill_Billid(Long billid);
}
