package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.RawChemical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawChemicalRepository extends JpaRepository<RawChemical, Long> { ;
}
