package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BottleRepository extends JpaRepository<Bottle, Long> {
    Optional<Bottle> findByBottleType_Bottleid(Long id);
}
