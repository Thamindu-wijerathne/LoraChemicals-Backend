package com.lorachemicals.Backend.dto;

public class RawChemicalResponseDTO {
    private Long chemid;
    private Long chemicalTypeId;
    private String chemicalTypeName;      // Useful for display
    private Long rmtid;
    private String rawMaterialTypeName;   // Useful for display
    private String type;
    private String volume;

    public RawChemicalResponseDTO() {}

    public Long getChemid() {
        return chemid;
    }

    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }

    public Long getChemicalTypeId() {
        return chemicalTypeId;
    }

    public void setChemicalTypeId(Long chemicalTypeId) {
        this.chemicalTypeId = chemicalTypeId;
    }

    public String getChemicalTypeName() {
        return chemicalTypeName;
    }

    public void setChemicalTypeName(String chemicalTypeName) {
        this.chemicalTypeName = chemicalTypeName;
    }

    public Long getrmtid() {
        return rmtid;
    }

    public void setrmtid(Long rmtid) {
        this.rmtid = rmtid;
    }

    public String getRawMaterialTypeName() {
        return rawMaterialTypeName;
    }

    public void setRawMaterialTypeName(String rawMaterialTypeName) {
        this.rawMaterialTypeName = rawMaterialTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}