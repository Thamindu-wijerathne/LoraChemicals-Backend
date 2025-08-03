package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.SupplierRawMaterial;
import com.lorachemicals.Backend.model.SupplierRawMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SupplierRawMaterialRepository extends JpaRepository<SupplierRawMaterial, SupplierRawMaterialId> {


    List<SupplierRawMaterial> findAllByOrderByExpDateAsc();

    @Query("""
    SELECT s FROM SupplierRawMaterial s
    WHERE TYPE(s.rawMaterial) = RawChemical
    AND TREAT(s.rawMaterial AS RawChemical).chemicalType.chemid = :chemid
    ORDER BY s.expDate ASC
""")
    List<SupplierRawMaterial> findByChemicalIdOrderByExpAsc(@Param("chemid") Long chemid);


}
