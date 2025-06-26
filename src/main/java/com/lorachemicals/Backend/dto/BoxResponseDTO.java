package com.lorachemicals.Backend.dto;

public class BoxResponseDTO {
    private Long boxid;

    private Long boxTypeId;
    private String boxTypeName;

    private Long rmtid;
    private String rawMaterialTypeName;

    private int quantity;

    public BoxResponseDTO() {}

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

    public String getBoxTypeName() {
        return boxTypeName;
    }

    public void setBoxTypeName(String boxTypeName) {
        this.boxTypeName = boxTypeName;
    }

    public Long getRmtid() {
        return rmtid;
    }

    public void setRmtid(Long rmtid) {
        this.rmtid = rmtid;
    }

    public String getRawMaterialTypeName() {
        return rawMaterialTypeName;
    }

    public void setRawMaterialTypeName(String rawMaterialTypeName) {
        this.rawMaterialTypeName = rawMaterialTypeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BoxResponseDTO{" +
                "boxid=" + boxid +
                ", boxTypeId=" + boxTypeId +
                ", boxTypeName='" + boxTypeName + '\'' +
                ", rmtid=" + rmtid +
                ", rawMaterialTypeName='" + rawMaterialTypeName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}