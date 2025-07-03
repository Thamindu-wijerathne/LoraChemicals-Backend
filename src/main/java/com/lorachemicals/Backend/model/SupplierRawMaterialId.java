package com.lorachemicals.Backend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Embeddable
public class SupplierRawMaterialId implements Serializable {

    private Long inventoryid;
    private Long supplierid;
    private LocalDate date;

    public SupplierRawMaterialId() {}

    public SupplierRawMaterialId(Long inventoryid, Long supplierid, LocalDate date) {
        this.inventoryid = inventoryid;
        this.supplierid = supplierid;
        this.date = date;
    }

    // Explicitly override equals and hashCode for embedded IDs
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierRawMaterialId)) return false;
        SupplierRawMaterialId that = (SupplierRawMaterialId) o;
        return Objects.equals(inventoryid, that.inventoryid) &&
                Objects.equals(supplierid, that.supplierid) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryid, supplierid, date);
    }
}