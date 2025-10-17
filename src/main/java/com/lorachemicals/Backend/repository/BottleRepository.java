package com.lorachemicals.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.Bottle;

public interface BottleRepository extends JpaRepository<Bottle, Long> {
    Optional<Bottle> findByBottleType_Bottleid(Long id);
    List<Bottle> findAllByBottleType_Bottleid(Long bottleTypeId);
}
