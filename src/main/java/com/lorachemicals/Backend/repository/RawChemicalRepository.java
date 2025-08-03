package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.model.RawChemicalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RawChemicalRepository extends JpaRepository<RawChemical, Long> {

    List<RawChemical> findByChemicalType(RawChemicalType rawChemicalType);

    Optional<RawChemical> findByChemicalType_Chemid(Long chemicalTypeChemid);
}
