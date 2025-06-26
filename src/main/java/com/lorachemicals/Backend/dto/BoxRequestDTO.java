package com.lorachemicals.Backend.dto;

public class BoxRequestDTO {
    private Long boxid; // This serves as both boxid and boxTypeId due to @MapsId
    private Long rmtid; // Refers to RawMaterialType.id
    private int quantity;

    public BoxRequestDTO() {}

    public Long getBoxid() {
        return boxid;
    }

    public void setBoxid(Long boxid) {
        this.boxid = boxid;
    }

    public Long getRmtid() {
        return rmtid;
    }

    public void setRmtid(Long rmtid) {
        this.rmtid = rmtid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BoxRequestDTO{" +
                "boxid=" + boxid +
                ", rmtid=" + rmtid +
                ", quantity=" + quantity +
                '}';
    }
}