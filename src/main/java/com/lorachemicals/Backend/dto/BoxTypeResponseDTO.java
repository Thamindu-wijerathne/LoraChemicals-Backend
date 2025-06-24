package com.lorachemicals.Backend.dto;

public class BoxTypeResponseDTO {

    private Long boxid;
    private int quantity_in_box;

    public BoxTypeResponseDTO() {}

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
