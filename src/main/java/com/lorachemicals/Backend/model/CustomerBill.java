package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "billid", nullable = false, foreignKey = @ForeignKey(name = "fk_customerbill_bill"))
    private Bill bill;

}
