package com.lorachemicals.Backend.dto;

public class LabelResponseDTO {
    private Long labelid;
    private String labeltypeName;
    private String labeltypeVolume;
    private Long rmtid;
    private String rawMaterialTypeName;
    private int quantity;

    public LabelResponseDTO() {}

    public Long getLabelid() { return labelid; }
    public void setLabelid(Long labelid) { this.labelid = labelid; }

    public String getLabeltypeName() { return labeltypeName; }
    public void setLabeltypeName(String labeltypeName) { this.labeltypeName = labeltypeName; }

    public String getLabeltypeVolume() { return labeltypeVolume; }
    public void setLabeltypeVolume(String labeltypeVolume) { this.labeltypeVolume = labeltypeVolume; }

    public Long getRawMaterialTypeId() { return rmtid; }
    public void setRawMaterialTypeId(Long rmtid) { this.rmtid = rmtid; }

    public String getRawMaterialTypeName() { return rawMaterialTypeName; }
    public void setRawMaterialTypeName(String rawMaterialTypeName) { this.rawMaterialTypeName = rawMaterialTypeName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "LabelResponseDTO{" + "labelid=" + labelid + ", labeltypeName='" + labeltypeName + '\'' + ", labeltypeVolume='" + labeltypeVolume + '\'' + ", rmtid=" + rmtid + ", rawMaterialTypeName='" + rawMaterialTypeName + '\'' + ", quantity=" + quantity + '}';
    }
}