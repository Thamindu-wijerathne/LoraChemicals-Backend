package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Mixer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MixerRepository extends JpaRepository<Mixer, Long> {

    @Query("SELECT m FROM Mixer m WHERE m.productType.productTypeId = :productTypeId AND m.availability = 1")
    List<Mixer> findAllByProductTypeId(@Param("productTypeId") Long productTypeId);

}
