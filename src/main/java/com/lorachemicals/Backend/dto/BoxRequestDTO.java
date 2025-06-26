package com.lorachemicals.Backend.dto;

public class BoxRequestDTO {
    private Long boxid; // Optional, for update or where required

    private Long boxTypeId; // Refers to BoxType.boxid
    private Long rmtid;     // Refers to RawMaterialType.rmtid

    private int quantity;

    public BoxRequestDTO() {}

    public Long getBoxid() {
        return boxid;
    }

    public void setBoxid(Long boxid) {
        this.boxid = boxid;
    }

    public Long getBoxTypeId() {
        return boxTypeId;
    }

    public void setBoxTypeId(Long boxTypeId) {
        this.boxTypeId = boxTypeId;
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
                ", boxTypeId=" + boxTypeId +
                ", rmtid=" + rmtid +
                ", quantity=" + quantity +
                '}';
    }
}