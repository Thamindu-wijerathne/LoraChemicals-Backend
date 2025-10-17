package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.CustomerBill;

public interface CustomerBillRepository extends JpaRepository<CustomerBill, Long> {
    CustomerBill findByBill_Billid(Long billid);
    List<CustomerBill> findByDelivery_Deliveryid(Long deliveryid);
    List<CustomerBill> findByBill_SalesRep_Srepid(Long srepid);
}
