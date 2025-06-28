package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.RawChemical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawChemicalRepository extends JpaRepository<RawChemical, Long> {

    @Query("SELECT r.chemicalType.chemid, r.chemicalType.name, SUM(r.volume) " +
            "FROM RawChemical r GROUP BY r.chemicalType.chemid, r.chemicalType.name")
    List<Object[]> sumVolumeGroupedByChemid();
}
