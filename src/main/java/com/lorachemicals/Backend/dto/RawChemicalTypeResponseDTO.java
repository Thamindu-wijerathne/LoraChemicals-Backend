package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawChemicalTypeResponseDTO {
    private Long chemid;

    private String name;

    private String type;

    private String description;

    public RawChemicalTypeResponseDTO() {}

    public RawChemicalTypeResponseDTO(Long chemid, String name, String description, String type) {
        this.chemid = chemid;
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
