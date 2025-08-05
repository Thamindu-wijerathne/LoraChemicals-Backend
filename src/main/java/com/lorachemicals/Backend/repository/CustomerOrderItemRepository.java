package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.CustomerOrderItem;
import com.lorachemicals.Backend.dto.TrendingProductsDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerOrderItemRepository extends JpaRepository<CustomerOrderItem, Long> {

    @Query("""
    SELECT new com.lorachemicals.Backend.dto.TrendingProductsDTO(
        p.name, p.unitPrice, p.image, SUM(i.quantity)
    )
    FROM CustomerOrderItem i
    JOIN i.productTypeVolume p
    GROUP BY p.name, p.unitPrice, p.image
    ORDER BY SUM(i.quantity) DESC
    """)
    List<TrendingProductsDTO> findTrendingProducts(Pageable pageable);
}
