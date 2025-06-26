package com.lorachemicals.Backend.dto;

public class LabelRequestDTO {
    private Long labelid; // This serves as both boxid and boxTypeId due to @MapsId
    private Long rmtid; // Refers to RawMaterialType.id
    private int quantity;

    public LabelRequestDTO() {}

    public Long getLabelid() {
        return labelid;
    }
    public void setLabelid(Long labelid) {
        this.labelid = labelid;
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
        return "LabelRequestDTO{" +
                "labelid=" + labelid +
                ", rmtid=" + rmtid +
                ", quantity=" + quantity +
                '}';
    }

}