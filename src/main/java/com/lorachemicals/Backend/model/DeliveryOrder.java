package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "delivery_order")
public class DeliveryOrder {

    @EmbeddedId
    private DeliveryOrderId id;

    @ManyToOne
    @MapsId("deliveryid")
    @JoinColumn(name = "deliveryid", nullable = false)
    private Delivery delivery;

    @ManyToOne
    @MapsId("orderid")
    @JoinColumn(name = "orderid", nullable = false)
    private CustomerOrder customerOrder;

    public DeliveryOrder() {}

    public DeliveryOrder(Delivery delivery, CustomerOrder customerOrder) {
        this.delivery = delivery;
        this.customerOrder = customerOrder;
        this.id = new DeliveryOrderId(delivery.getDeliveryid(), customerOrder.getOrderid());
    }
}
