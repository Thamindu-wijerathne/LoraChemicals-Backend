package com.lorachemicals.Backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.lorachemicals.Backend.model.BatchInventoryDelivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lorachemicals.Backend.model.BatchInventory;

public interface BatchInventoryRepository extends JpaRepository<BatchInventory, Long> {
    
    @Query("SELECT bi FROM BatchInventory bi LEFT JOIN FETCH bi.parentBatchType")
    List<BatchInventory> findAllWithParentBatchType();
    
    @Query("SELECT bi FROM BatchInventory bi WHERE bi.parentBatchType.id = :batchTypeId")
    List<BatchInventory> findByParentBatchTypeId(@Param("batchTypeId") Long batchTypeId);

    @Query("SELECT bi FROM BatchInventory bi " +
            "LEFT JOIN FETCH bi.parentBatchType " +
            "WHERE bi.batch_quantity <= :threshold " +
            "ORDER BY bi.batch_quantity ASC, bi.inventoryid DESC")
    List<BatchInventory> findLastFiveLowStock(@Param("threshold") Integer threshold, Pageable pageable);


}
