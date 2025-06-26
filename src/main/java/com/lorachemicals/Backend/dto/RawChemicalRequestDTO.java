package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawChemicalRequestDTO {

    // Getters and setters
    private Long chemid;
    private Double volume;

    // Constructor(s)
    public RawChemicalRequestDTO() {}

    public RawChemicalRequestDTO(Long chemid, Double volume) {
        this.chemid = chemid;
        this.volume = volume;
    }

}
