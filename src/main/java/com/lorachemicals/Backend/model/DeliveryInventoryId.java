package com.lorachemicals.Backend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class DeliveryInventoryId implements Serializable {

    private Long deliveryid;
    private Long inventoryid;
    private LocalDateTime deliverydate;

    public DeliveryInventoryId() {}

    public DeliveryInventoryId(Long deliveryid, Long inventoryid, LocalDateTime deliverydate) {
        this.deliveryid = deliveryid;
        this.inventoryid = inventoryid;
        this.deliverydate = deliverydate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryInventoryId)) return false;
        DeliveryInventoryId that = (DeliveryInventoryId) o;
        return Objects.equals(deliveryid, that.deliveryid) &&
                Objects.equals(inventoryid, that.inventoryid) &&
                Objects.equals(deliverydate, that.deliverydate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryid, inventoryid, deliverydate);
    }
}
