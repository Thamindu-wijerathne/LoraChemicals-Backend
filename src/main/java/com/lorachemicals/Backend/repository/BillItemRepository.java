package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Bill;
import com.lorachemicals.Backend.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {

}
