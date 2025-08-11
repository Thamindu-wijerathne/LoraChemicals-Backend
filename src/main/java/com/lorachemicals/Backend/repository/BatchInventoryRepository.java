package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lorachemicals.Backend.model.BatchInventory;

public interface BatchInventoryRepository extends JpaRepository<BatchInventory, Long> {
    
    @Query("SELECT bi FROM BatchInventory bi LEFT JOIN FETCH bi.parentBatchType")
    List<BatchInventory> findAllWithParentBatchType();
    
    @Query("SELECT bi FROM BatchInventory bi WHERE bi.parentBatchType.id = :batchTypeId")
    List<BatchInventory> findByParentBatchTypeId(@Param("batchTypeId") Long batchTypeId);
}
