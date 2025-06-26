package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
