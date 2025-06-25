package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "box_type")
public class BoxType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxid;
    private int quantity_in_box;
    private String name;

    public BoxType() {}

    public void setBoxid(Long boxid) {
        this.boxid = boxid;
    }

    public Long getBoxid() {
        return boxid;
    }

    public void setquantity_in_box(int quantity_in_box) {
        this.quantity_in_box = quantity_in_box;
    }

    public int getquantity_in_box() {
        return quantity_in_box;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

}
