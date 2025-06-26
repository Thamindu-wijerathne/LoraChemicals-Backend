package com.lorachemicals.Backend.dto;

public class BoxResponseDTO {
    private Long boxid;

    private Long boxTypeId;
    private String boxTypeName; // If you want to display BoxType info

    private Long rawMaterialTypeId;
    private String rawMaterialTypeName; // Optional descriptive info

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

    public Long getRawMaterialTypeId() {
        return rawMaterialTypeId;
    }

    public void setRawMaterialTypeId(Long rawMaterialTypeId) {
        this.rawMaterialTypeId = rawMaterialTypeId;
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
}
