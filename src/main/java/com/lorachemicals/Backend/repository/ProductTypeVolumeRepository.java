package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.ProductTypeVolume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeVolumeRepository extends JpaRepository<ProductTypeVolume, Long> {
    List<ProductTypeVolume> findByProductType_ProductTypeId(Long id);
}
