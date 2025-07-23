package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveryinventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryInventory {

    @EmbeddedId
    private DeliveryInventoryId deliveryInventoryId;

    @MapsId("deliveryid")
    @ManyToOne
    @JoinColumn(name = "deliveryid", nullable = false)
    private Delivery delivery;

    @MapsId("inventoryid")
    @ManyToOne
    @JoinColumn(name = "inventoryid", nullable = false)
    private Inventory inventory;


    private int quantity;
    private int currentQuantity;

    @ManyToOne
    @JoinColumn(name = "wmid")
    private WarehouseManager warehouseManager;

}
