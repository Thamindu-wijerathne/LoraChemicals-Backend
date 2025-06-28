package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawChemicalRequestDTO {

    // Getters and setters
    private Long chemid;
    private Double volume;
    private String location;

    // Constructor(s)
    public RawChemicalRequestDTO() {}


}
