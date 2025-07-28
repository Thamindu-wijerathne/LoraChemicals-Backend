package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> findBySalesRep_Srepid(Long srepid);

}
