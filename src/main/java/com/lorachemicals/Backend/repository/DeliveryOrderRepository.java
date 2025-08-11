package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.DamageReport;
import com.lorachemicals.Backend.model.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    List<DeliveryOrder> findByDelivery_Deliveryid(Long deliveryId);

}
