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
            @UniqueConstraint(columnNames = "batchtypewithoutboxid")
        }
)
@PrimaryKeyJoinColumn(name = "inventoryid")

@Getter
@Setter
public class BatchInventoryWithoutBox extends Inventory {

    @ManyToOne
    @JoinColumn(name = "batchtypewithoutboxid", nullable = false)
    private BatchTypeWithoutBox batchTypeWithoutBox;

    private int batch_quantity;

    public void setId(Long id) {
        this.setInventoryid(id);
    }
}
