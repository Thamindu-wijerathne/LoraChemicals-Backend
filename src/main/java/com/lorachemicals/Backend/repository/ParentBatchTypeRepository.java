package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.ParentBatchType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParentBatchTypeRepository extends JpaRepository<ParentBatchType, Long> {

    List<ParentBatchType> findByProductTypeVolume(ProductTypeVolume productTypeVolume);

    Optional<ParentBatchType> findByUniqueBatchCode(String uniqueBatchCode);

    @Query("SELECT p FROM ParentBatchType p WHERE p.uniqueBatchCode LIKE :prefix%")
    List<ParentBatchType> findByUniqueBatchCodeStartingWith(@Param("prefix") String prefix);

    boolean existsByUniqueBatchCode(String uniqueBatchCode);
}
