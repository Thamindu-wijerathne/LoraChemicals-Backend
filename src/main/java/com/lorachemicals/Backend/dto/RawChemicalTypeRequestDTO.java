package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawChemicalTypeRequestDTO {

    private String name;

    private String type;

    private String description;

    public RawChemicalTypeRequestDTO() {}

}
