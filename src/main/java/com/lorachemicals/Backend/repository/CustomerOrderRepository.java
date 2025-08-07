package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.dto.DistrictSalesDTO;
import com.lorachemicals.Backend.dto.SalesEmployeeDTO;
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


    @Query("""
    SELECT new com.lorachemicals.Backend.dto.SalesEmployeeDTO(
        CONCAT(srUser.fname, ' ', srUser.lname),
        CAST(SUM(o.total) AS double),
        COUNT(o.orderid)
    )
    FROM CustomerOrder o
    JOIN o.user u
    JOIN Customer c ON c.user = u
    JOIN c.salesRep sr
    JOIN sr.user srUser
    GROUP BY srUser.fname, srUser.lname
""")
    List<SalesEmployeeDTO> getSalesByEmployee(Pageable pageable);

}


