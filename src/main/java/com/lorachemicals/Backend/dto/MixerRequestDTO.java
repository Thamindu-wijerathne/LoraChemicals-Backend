package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerRequestDTO {
    private String name;
    private long capacity;
    private int availability;

    public MixerRequestDTO() {}
    public MixerRequestDTO(String name, long capacity, int availability) {
        this.name = name;
        this.capacity = capacity;
        this.availability = availability;
    }
}
