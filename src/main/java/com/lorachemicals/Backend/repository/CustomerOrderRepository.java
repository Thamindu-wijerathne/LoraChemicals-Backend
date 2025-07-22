package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUser_Id(Long customerId);

    CustomerOrder findByOrderid(Long orderid);
}
