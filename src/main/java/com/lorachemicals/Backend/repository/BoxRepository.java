package com.lorachemicals.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lorachemicals.Backend.dto.BoxResponseDTO;
import com.lorachemicals.Backend.model.Box;

public interface BoxRepository extends JpaRepository<Box, Long> {

    @Query("SELECT new com.lorachemicals.Backend.dto.BoxResponseDTO(" +
            "b.inventoryid, b.boxType.boxid, b.quantity, b.location) " +
            "FROM Box b WHERE b.boxType.boxid = :boxid")
    BoxResponseDTO findByBoxType_Boxid(@Param("boxid") Long boxid);

}
