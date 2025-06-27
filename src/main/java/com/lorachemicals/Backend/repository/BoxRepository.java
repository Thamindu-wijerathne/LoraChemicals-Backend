package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Long> {

    @Query("SELECT b.boxType.boxid, b.boxType.name, b.boxType.quantityInBox, SUM(b.quantity) " +
            "FROM Box b GROUP BY b.boxType.boxid, b.boxType.name, b.boxType.quantityInBox")
    List<Object[]> sumQuantityGroupedByBoxType();
}
