package com.lorachemicals.Backend.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChemicalUsageId implements Serializable {

    private Long inventoryid;
    private Long prodid;

    public ChemicalUsageId() {}

    public ChemicalUsageId(Long inventoryid, Long prodid) {
        this.inventoryid = inventoryid;
        this.prodid = prodid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChemicalUsageId)) return false;
        ChemicalUsageId that = (ChemicalUsageId) o;
        return Objects.equals(inventoryid, that.inventoryid) &&
                Objects.equals(prodid, that.prodid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryid, prodid);
    }
}
