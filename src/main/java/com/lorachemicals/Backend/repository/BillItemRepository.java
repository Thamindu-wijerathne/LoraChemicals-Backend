package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {
}
