package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "batch_inventory",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "batchtypeid")
        }
)
@PrimaryKeyJoinColumn(name = "inventoryid")

@Getter
@Setter
public class BatchInventory extends Inventory{
    @ManyToOne
    @JoinColumn(name = "batchtypeid", nullable = false)
    private BatchType batchType;

    private int batch_quantity;

    public void setId(Long id) {
        this.setInventoryid(id);
    }

}
