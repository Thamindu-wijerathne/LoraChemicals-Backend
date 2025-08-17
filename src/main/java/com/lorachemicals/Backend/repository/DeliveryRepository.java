package com.lorachemicals.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorachemicals.Backend.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findBySalesRep_Srepid(Long srepid);
    List<Delivery> findByRoute_Routeid(Long routeid);
    List<Delivery> findByVehicle_Id(Long vehicleid);
    List<Delivery> findByStatus(int status);
    List<Delivery> findBySalesRep_SrepidAndStatus(Long srepid, int status);
}
