package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerResponseDTO {
    private Long mixerid;
    private String name;
    private long capacity;
    private int availability;

    public MixerResponseDTO() {}

    public MixerResponseDTO(Long mixerid, String name, long capacity,int availability) {
        this.mixerid = mixerid;
        this.name = name;
        this.capacity = capacity;
        this.availability = availability;
    }

}