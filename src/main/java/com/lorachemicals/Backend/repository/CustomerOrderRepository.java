package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.BoxType;
import com.lorachemicals.Backend.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
