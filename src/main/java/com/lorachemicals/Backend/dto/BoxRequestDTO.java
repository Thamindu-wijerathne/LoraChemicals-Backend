package com.lorachemicals.Backend.dto;

public class BoxRequestDTO {
    private Long boxid; // Optional for creation if auto-generated

    private Long boxTypeId; // Refers to BoxType.boxid
    private Long rawMaterialTypeId; // Refers to RawMaterialType.id

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

    public Long getRawMaterialTypeId() {
        return rawMaterialTypeId;
    }

    public void setRawMaterialTypeId(Long rawMaterialTypeId) {
        this.rawMaterialTypeId = rawMaterialTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
