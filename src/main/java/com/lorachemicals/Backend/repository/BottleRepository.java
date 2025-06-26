package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BottleRepository extends JpaRepository<Bottle, Long> {

    @Query("SELECT b.bottleType.bottleid, b.bottleType.name, b.bottleType.volume, SUM(b.quantity) " +
            "FROM Bottle b GROUP BY b.bottleType.bottleid, b.bottleType.name, b.bottleType.volume")
    List<Object[]> sumQuantityGroupedByBottleType();
}
