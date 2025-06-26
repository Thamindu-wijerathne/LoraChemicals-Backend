package com.lorachemicals.Backend.dto;

public class RawChemicalTypeResponseDTO {
    private Long chemid;

    private String name;

    private String type;

    private String description;

    public RawChemicalTypeResponseDTO() {}

    public Long getChemid() {
        return chemid;
    }

    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
