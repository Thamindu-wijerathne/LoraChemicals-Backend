package com.lorachemicals.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "batch_inventory_without_box",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "parent_batch_type_id")
        }
)
@PrimaryKeyJoinColumn(name = "inventoryid")

@Getter
@Setter
public class BatchInventoryWithoutBox extends Inventory {

    @ManyToOne
    @JoinColumn(name = "parent_batch_type_id", nullable = false)
    private ParentBatchType parentBatchType;

    private int batch_quantity;

    public void setId(Long id) {
        this.setInventoryid(id);
    }

    // Backward compatibility methods
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
