package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.dto.BatchTypeResponseDTO;
import com.lorachemicals.Backend.model.BatchType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchTypeRepository extends JpaRepository <BatchType,Long>{
    List<BatchType> findByProductTypeVolume(ProductTypeVolume productTypeVolume);

}
