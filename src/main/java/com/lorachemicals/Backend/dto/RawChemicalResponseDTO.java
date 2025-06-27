package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawChemicalResponseDTO {

    private Long inventoryid;
    private Long chemid;
    private String chemicalName;
    private Double volume;

    public RawChemicalResponseDTO() {
    }

    public RawChemicalResponseDTO(Long inventoryid, Long chemid, String chemicalName, Double volume) {
        this.inventoryid = inventoryid;
        this.chemid = chemid;
        this.chemicalName = chemicalName;
        this.volume = volume;
    }

}
