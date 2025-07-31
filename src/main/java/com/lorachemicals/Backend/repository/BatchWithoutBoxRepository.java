package com.lorachemicals.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.BatchWithoutBox;

public interface BatchWithoutBoxRepository extends JpaRepository<BatchWithoutBox, Long> {
}
