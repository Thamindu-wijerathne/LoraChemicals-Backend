package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "batch_type")
@PrimaryKeyJoinColumn(name = "id")
public class BatchType extends ParentBatchType {

    @ManyToOne
    @JoinColumn(name = "boxid", nullable = false)
    private BoxType boxType;

    public BatchType() {
    }

    // Override getId to return the parent's id
    public Long getBatchtypeid() {
        return super.getId();
    }

    // Setter for compatibility
    public void setBatchtypeid(Long id) {
        super.setId(id);
    }
}
