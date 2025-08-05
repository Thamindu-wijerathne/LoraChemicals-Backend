package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.dto.TrendingProductsDTO;
import com.lorachemicals.Backend.model.CustomerOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUser_Id(Long customerId);

    CustomerOrder findByOrderid(Long orderid);

}


