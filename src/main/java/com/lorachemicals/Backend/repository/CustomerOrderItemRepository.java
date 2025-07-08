package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.CustomerOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderItemRepository extends JpaRepository<CustomerOrderItem, Long> {
}
