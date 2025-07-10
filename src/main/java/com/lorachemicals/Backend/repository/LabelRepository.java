package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findByLabeltype_LabelId(Long labelId);
}
