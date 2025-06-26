package com.lorachemicals.Backend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
public class SupplierRawMaterialId implements Serializable {

    private Long rmtid;
    private Long supplierid;
    private LocalDate date;

    public SupplierRawMaterialId() {}

    public SupplierRawMaterialId(Long rmtid, Long supplierid, LocalDate date) {
        this.rmtid = rmtid;
        this.supplierid = supplierid;
        this.date = date;
    }
}
