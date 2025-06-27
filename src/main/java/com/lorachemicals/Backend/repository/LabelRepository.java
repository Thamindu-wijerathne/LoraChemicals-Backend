package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query("SELECT l.labeltype.labelId, l.labeltype.name, SUM(l.quantity) " +
            "FROM Label l GROUP BY l.labeltype.labelId, l.labeltype.name")
    List<Object[]> sumQuantityGroupedByLabeltype();
}
