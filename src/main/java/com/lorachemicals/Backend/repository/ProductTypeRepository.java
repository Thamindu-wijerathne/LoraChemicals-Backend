package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
