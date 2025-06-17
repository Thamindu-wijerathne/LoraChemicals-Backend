package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customer by user ID
    Customer findByUserId(Long userId);

    // Find customer by sales rep ID
    java.util.List<Customer> findBySalesRepSrepid(Long srepid);

    // Find customer by route ID
    java.util.List<Customer> findByRouteRouteid(Long routeid);

}
