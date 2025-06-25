package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "box")
public class Box extends RawMaterial {

    @OneToOne
    @MapsId // Inherited id used as both PK and FK to BoxType
    @JoinColumn(name = "boxid")
    private BoxType boxtype;

    private int quantity;

    public BoxType getBoxtype() {
        return boxtype;
    }

    public void setBoxtype(BoxType boxtype) {
        this.boxtype = boxtype;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
