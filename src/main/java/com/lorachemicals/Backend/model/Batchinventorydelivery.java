package com.lorachemicals.Backend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private ParentBatchType batchType;

    @MapsId("deliveryid")  // maps to deliveryid in BatchInventoryDeliveryId
    @ManyToOne
    @JoinColumn(name = "deliveryid", referencedColumnName = "deliveryid")
    private Delivery delivery;

    private Integer quantity;
    private Integer currentQuantity;

    @ManyToOne
    @JoinColumn(name = "wmid", referencedColumnName = "wmid")
    private WarehouseManager warehouseManager;
}
