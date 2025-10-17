package com.lorachemicals.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer_bill")
public class CustomerBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cbillid;

    private String shop_name;

    private String address;

    private String phone;

    private String district;

    @OneToOne
    @JoinColumn(name = "billid", nullable = false, foreignKey = @ForeignKey(name = "fk_customerbill_bill"))
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "deliveryid", nullable = false, foreignKey = @ForeignKey(name = "fk_customerbill_delivery"))
    private Delivery delivery;

}
