package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.SupplierRawMaterial;
import com.lorachemicals.Backend.model.SupplierRawMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SupplierRawMaterialRepository extends JpaRepository<SupplierRawMaterial, SupplierRawMaterialId> {
    List<SupplierRawMaterial> findAllByOrderByExpDateDesc();
}
