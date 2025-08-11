package com.lorachemicals.Backend.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class DeliveryOrderId implements Serializable {

    private Long deliveryid;
    private Long orderid;

    public DeliveryOrderId() {}

    public DeliveryOrderId(Long deliveryid, Long orderid) {
        this.deliveryid = deliveryid;
        this.orderid = orderid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryOrderId)) return false;
        DeliveryOrderId that = (DeliveryOrderId) o;
        return Objects.equals(deliveryid, that.deliveryid) &&
                Objects.equals(orderid, that.orderid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryid, orderid);
    }

}
