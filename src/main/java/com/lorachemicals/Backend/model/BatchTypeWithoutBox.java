package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "batch_type_without_box")
@PrimaryKeyJoinColumn(name = "id")
public class BatchTypeWithoutBox extends ParentBatchType {

    public BatchTypeWithoutBox() {
    }

    // Override getId to return the parent's id
    public Long getBatchtypewithoutboxid() {
        return super.getId();
    }

    // Setter for compatibility
    public void setBatchtypewithoutboxid(Long id) {
        super.setId(id);
    }
}
