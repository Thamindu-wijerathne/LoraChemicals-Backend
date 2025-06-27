package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier_rawmaterial")
public class SupplierRawMaterial {

    @EmbeddedId
    private SupplierRawMaterialId id;

    @MapsId("supplierid")  // from SupplierRawMaterialId
    @ManyToOne
    @JoinColumn(name = "supplierid", referencedColumnName = "supplierid")
    private Supplier supplier;

    @MapsId("rmtid")  // from SupplierRawMaterialId, matches inventoryid of RawMaterial (named rmtid in this table)
    @ManyToOne
    @JoinColumn(name = "rmtid", referencedColumnName = "inventoryid")
    private RawMaterial rawMaterial;

    @Column(name = "exp_date")
    private LocalDate expDate;

    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;
}
