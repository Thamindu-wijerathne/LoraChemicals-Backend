package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.dto.DistrictSalesDTO;
import com.lorachemicals.Backend.dto.TrendingProductsDTO;
import com.lorachemicals.Backend.model.CustomerOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUser_Id(Long customerId);

    CustomerOrder findByOrderid(Long orderid);

    // CustomerOrderRepository.java
    @Query("""
    SELECT new com.lorachemicals.Backend.dto.DistrictSalesDTO(r.district, SUM(o.total))
    FROM CustomerOrder o
    JOIN o.user u
    JOIN Customer c ON c.user = u
    JOIN c.route r
    GROUP BY r.district
""")
    List<DistrictSalesDTO> getTotalSalesPerDistrict();


}


