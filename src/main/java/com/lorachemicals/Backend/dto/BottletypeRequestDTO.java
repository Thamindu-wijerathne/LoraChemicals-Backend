package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BottletypeRequestDTO {

    private String name;
    private String volume;

    public BottletypeRequestDTO() {}

    public BottletypeRequestDTO(String name, String volume) {
        this.name = name;
        this.volume = volume;
    }
}