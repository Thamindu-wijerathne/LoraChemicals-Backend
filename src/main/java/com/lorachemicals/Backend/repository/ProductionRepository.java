package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductionRepository extends JpaRepository<Production, Long> {
    Optional<Production> findTopByProductype_ProductTypeIdOrderByExpiredateAsc(Long ptid);

    Optional<Production> findTopByStatusIgnoreCaseOrderByExpiredateAsc(String confirmed);

    Optional<Production> getByProdid(Long prodid);
}
