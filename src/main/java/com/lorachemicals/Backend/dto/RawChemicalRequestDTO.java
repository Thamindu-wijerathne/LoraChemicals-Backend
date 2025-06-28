package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RawChemicalRequestDTO {

    private Long chemid;
    private Double volume;
    private String location;

}
