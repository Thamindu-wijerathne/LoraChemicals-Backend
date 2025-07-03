package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BottleRepository extends JpaRepository<Bottle, Long> {
}
