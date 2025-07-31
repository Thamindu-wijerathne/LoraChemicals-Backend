package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.BatchTypeWithoutBox;
import com.lorachemicals.Backend.model.ProductTypeVolume;

public interface BatchTypeWithoutBoxRepository extends JpaRepository<BatchTypeWithoutBox, Long> {

    List<BatchTypeWithoutBox> findByProductTypeVolume(ProductTypeVolume productTypeVolume);
}
