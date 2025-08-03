package com.lorachemicals.Backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batch_without_box")
public class BatchWithoutBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchwithoutboxid;

    @ManyToOne
    @JoinColumn(name = "parent_batch_type_id", nullable = false)
    private ParentBatchType parentBatchType;

    private LocalDateTime batchdate;

    @ManyToOne
    @JoinColumn(name = "wmid", nullable = false)
    private WarehouseManager warehousemanager;

    @ManyToOne
    @JoinColumn(name = "prodid", nullable = false)
    private Production production;

    private String status;

    private int quantity;

    @Column(unique = true, nullable = false)
    private String batchcode;

    // Backward compatibility methods
    public BatchTypeWithoutBox getBatchtypewithoutbox() {
        if (parentBatchType instanceof BatchTypeWithoutBox) {
            return (BatchTypeWithoutBox) parentBatchType;
        }
        return null;
    }

    public void setBatchtypewithoutbox(BatchTypeWithoutBox batchTypeWithoutBox) {
        this.parentBatchType = batchTypeWithoutBox;
    }
}
