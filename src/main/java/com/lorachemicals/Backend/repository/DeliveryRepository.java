package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
