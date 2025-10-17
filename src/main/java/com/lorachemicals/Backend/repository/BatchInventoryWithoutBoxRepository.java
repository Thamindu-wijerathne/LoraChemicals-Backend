package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lorachemicals.Backend.model.BatchInventoryWithoutBox;
import com.lorachemicals.Backend.model.ParentBatchType;

public interface BatchInventoryWithoutBoxRepository extends JpaRepository<BatchInventoryWithoutBox, Long> {

    List<BatchInventoryWithoutBox> findByParentBatchTypeIn(List<ParentBatchType> batchTypes);
    
    @Query("SELECT biwb FROM BatchInventoryWithoutBox biwb LEFT JOIN FETCH biwb.parentBatchType")
    List<BatchInventoryWithoutBox> findAllWithParentBatchType();
    
    @Query("SELECT biwb FROM BatchInventoryWithoutBox biwb WHERE biwb.parentBatchType.id = :batchTypeId")
    List<BatchInventoryWithoutBox> findByParentBatchTypeId(@Param("batchTypeId") Long batchTypeId);

    @Query("SELECT biwb FROM BatchInventoryWithoutBox biwb " +
            "WHERE biwb.batch_quantity <= :threshold " +
            "ORDER BY biwb.inventoryid DESC")
    List<BatchInventoryWithoutBox> findLowStockWithParentBatchType(@Param("threshold") int threshold);

}
