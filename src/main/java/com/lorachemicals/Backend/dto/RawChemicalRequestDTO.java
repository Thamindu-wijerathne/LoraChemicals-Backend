package com.lorachemicals.Backend.dto;

public class RawChemicalRequestDTO {
    private Long chemid;                 // For updates, optional for create
    private Long rmtid;      // Reference to RawMaterialType.id
    private String type;
    private String volume;

    public RawChemicalRequestDTO() {}

    //getters and setters
    public Long getChemid() {
        return this.chemid;
    }
    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }
    public Long getRmtid() {
        return this.rmtid;
    }
    public void setRmtid(Long rmtid) {
        this.rmtid = rmtid;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getVolume() {
        return this.volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "RawChemicalRequestDTO{" +
                "chemid=" + chemid +
                ", rmtid=" + rmtid +
                ", type='" + type + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}