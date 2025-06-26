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

    // FK to RawMaterialType (assuming PK is id not rmtid, adjust if necessary)
    @MapsId("rmtid")
    @ManyToOne
    @JoinColumn(name = "rmtid", referencedColumnName = "id")
    private RawMaterialType rawMaterialType;

    @Column(name = "exp_date")
    private LocalDate expDate;

    private int quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

}