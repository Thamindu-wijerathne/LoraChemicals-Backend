package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductionRepository extends JpaRepository<Production, Long> {
    Optional<Production> findTopByProductype_ProductTypeIdOrderByExpiredateAsc(Long ptid);

    Optional<Production> findTopByStatusIgnoreCaseOrderByExpiredateAsc(String confirmed);

    List<Production> findByProductype_ProductTypeIdAndStatusOrderByExpiredateAsc(Long ptid, String status);

}
