package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchid;

    @ManyToOne
    @JoinColumn(name = "parent_batch_type_id", nullable = false)
    private ParentBatchType parentBatchType;

    private LocalDateTime batchdate;

    @ManyToOne
    @JoinColumn(name = "inventoryid", nullable = false)
    private Box box;

    @ManyToOne
    @JoinColumn(name = "wmid", nullable = false)
    private WarehouseManager warehousemanager;

    @ManyToOne
    @JoinColumn(name = "prodid", nullable = false)
    private Production production;

    private String status;

    private int quantity;

    // Backward compatibility methods
    public BatchType getBatchtype() {
        if (parentBatchType instanceof BatchType) {
            return (BatchType) parentBatchType;
        }
        return null;
    }

    public void setBatchtype(BatchType batchType) {
        this.parentBatchType = batchType;
    }

    public BatchTypeWithoutBox getBatchtypeWithoutBox() {
        if (parentBatchType instanceof BatchTypeWithoutBox) {
            return (BatchTypeWithoutBox) parentBatchType;
        }
        return null;
    }

    public void setBatchtypeWithoutBox(BatchTypeWithoutBox batchTypeWithoutBox) {
        this.parentBatchType = batchTypeWithoutBox;
    }
}
