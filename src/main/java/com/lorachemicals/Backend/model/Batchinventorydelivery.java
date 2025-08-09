package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deliverybatchinventory")
public class BatchInventoryDelivery {

    @EmbeddedId
    private BatchInventoryDeliveryId id;

    @MapsId("batchtypeid")  // maps to batchtypeid in BatchInventoryDeliveryId
    @ManyToOne
    @JoinColumn(name = "batchtypeid", referencedColumnName = "id")
    private BatchType batchType;

    @MapsId("deliveryid")  // maps to deliveryid in BatchInventoryDeliveryId
    @ManyToOne
    @JoinColumn(name = "deliveryid", referencedColumnName = "deliveryid")
    private Delivery delivery;

    private String type;

    private Integer quantity;
    private Integer currentQuantity;

    @ManyToOne
    @JoinColumn(name = "wmid", referencedColumnName = "wmid")
    private WarehouseManager warehouseManager;
}
