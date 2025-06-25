package com.lorachemicals.Backend.dto;

public class BoxTypeRequestDTO {
    private int quantity_in_box;
    private String name;

    public BoxTypeRequestDTO() {}

    public void setquantity_in_box(int quantity_in_box) {
        this.quantity_in_box = quantity_in_box;
    }

    public int getquantity_in_box() {
        return quantity_in_box;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

}
