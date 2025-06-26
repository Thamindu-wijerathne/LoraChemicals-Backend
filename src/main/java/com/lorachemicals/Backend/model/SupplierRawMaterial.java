package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "supplier_rawmaterial")
public class SupplierRawMaterial {

    @EmbeddedId
    private SupplierRawMaterialId id;

    // FK to Supplier
    @MapsId("supplierid")
    @ManyToOne
    @JoinColumn(name = "supplierid", referencedColumnName = "supplierid")
    private Supplier supplier;

    // FK to RawMaterial
    @MapsId("rmtid")
    @ManyToOne
    @JoinColumn(name = "rmtid", referencedColumnName = "rmtid")
    private RawMaterial rawMaterial;

    @Column(name = "exp_date")
    private LocalDate expDate;

    private int quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

}
