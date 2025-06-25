package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bottle")
public class Bottle extends RawMaterial {

    @OneToOne
    @MapsId // Inherited id from RawMaterial
    @JoinColumn(name = "bottleid", nullable = false)
    private Bottletype bottletype;

    private int quantity;

    // Getters and setters

    public Bottletype getBottletype() {
        return bottletype;
    }

    public void setBottletype(Bottletype bottletype) {
        this.bottletype = bottletype;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
