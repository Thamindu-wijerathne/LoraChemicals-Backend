package com.lorachemicals.Backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.BatchInventoryDelivery;
import com.lorachemicals.Backend.model.BatchInventoryDeliveryId;

public interface BatchInventoryDeliveryRepository extends JpaRepository<BatchInventoryDelivery, BatchInventoryDeliveryId> {
    List<BatchInventoryDelivery> findById_Deliveryid(Long deliveryid);
    List<BatchInventoryDelivery> findById_Batchtypeid(Long batchtypeid);
    List<BatchInventoryDelivery> findById_Type(String type);
    List<BatchInventoryDelivery> findByWarehouseManager_Wmid(Long wmid);
    List<BatchInventoryDelivery> findByDelivery_Deliveryid(Long deliveryid);

    BatchInventoryDelivery findByBatchType_IdAndDelivery_DeliveryidAndId_TypeAndId_Datetime(Long batchtypeid, Long deliveryid, String type, LocalDateTime datetime);
}
