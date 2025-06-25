package com.lorachemicals.Backend.dto;

public class BoxTypeResponseDTO {

    private Long boxid;
    private int quantity_in_box;
    private String name;

    public BoxTypeResponseDTO() {}

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
