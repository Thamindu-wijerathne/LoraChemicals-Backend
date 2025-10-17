package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "batch_inventory",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "parent_batch_type_id")
        }
)
@PrimaryKeyJoinColumn(name = "inventoryid")

@Getter
@Setter
public class BatchInventory extends Inventory {

    @ManyToOne
    @JoinColumn(name = "parent_batch_type_id", nullable = false)
    private ParentBatchType parentBatchType;

    private Integer batch_quantity;

    public void setId(Long id) {
        this.setInventoryid(id);
    }

    // Backward compatibility methods
    public BatchType getBatchType() {
        if (parentBatchType instanceof BatchType) {
            return (BatchType) parentBatchType;
        }
        return null;
    }

    public void setBatchType(BatchType batchType) {
        this.parentBatchType = batchType;
    }

    public BatchTypeWithoutBox getBatchTypeWithoutBox() {
        if (parentBatchType instanceof BatchTypeWithoutBox) {
            return (BatchTypeWithoutBox) parentBatchType;
        }
        return null;
    }

    public void setBatchTypeWithoutBox(BatchTypeWithoutBox batchTypeWithoutBox) {
        this.parentBatchType = batchTypeWithoutBox;
    }
}
