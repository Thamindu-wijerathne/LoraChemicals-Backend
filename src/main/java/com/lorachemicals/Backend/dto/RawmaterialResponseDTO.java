package com.lorachemicals.Backend.dto;

public class RawmaterialResponseDTO {
    private Long rmtid;
    private Long rawMaterialTypeId;
    private String rawMaterialTypeName;
    private Long inventoryid;
    private String location;

    public RawmaterialResponseDTO() {}

    public RawmaterialResponseDTO(Long rmtid, Long rawMaterialTypeId, String rawMaterialTypeName, Long inventoryid) {
        this.rmtid = rmtid;
        this.rawMaterialTypeId = rawMaterialTypeId;
        this.rawMaterialTypeName = rawMaterialTypeName;
        this.inventoryid = inventoryid;
    }

    public Long getRmtid() {
        return rmtid;
    }

    public void setRmtid(Long rmtid) {
        this.rmtid = rmtid;
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

    public Long getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(Long inventoryid) {
        this.inventoryid = inventoryid;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "RawmaterialResponseDTO{" +
                "rmtid=" + rmtid +
                ", rawMaterialTypeId=" + rawMaterialTypeId +
                ", rawMaterialTypeName='" + rawMaterialTypeName + '\'' +
                ", inventoryid=" + inventoryid +
                ", location='" + location + '\'' +
                '}';
    }
}