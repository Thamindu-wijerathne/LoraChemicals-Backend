package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "box_type")
public class BoxType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxid;
    private int quantity_in_box;

    public BoxType() {}

    public void setBoxid(Long boxid) {
        this.boxid = boxid;
    }

    public Long getBoxid() {
        return boxid;
    }

    public void setQuantity_in_box(int quantity_in_box) {
        this.quantity_in_box = quantity_in_box;
    }

    public int getQuantity_in_box() {
        return quantity_in_box;
    }

}
