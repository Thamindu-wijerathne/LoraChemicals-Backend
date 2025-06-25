package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.RawMaterialType;
import com.lorachemicals.Backend.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialTypeRepository extends JpaRepository<RawMaterialType, Long> {
}
