package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BottletypeResponseDTO {

    private Long bottleid;
    private String name;
    private String volume;

    public BottletypeResponseDTO() {}

    public BottletypeResponseDTO(Long bottleid, String name, String volume) {
        this.bottleid = bottleid;
        this.name = name;
        this.volume = volume;
    }
}