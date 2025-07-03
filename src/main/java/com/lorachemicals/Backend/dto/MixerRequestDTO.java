package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerRequestDTO {
    private String name;
    private long capacity;
    private Long productTypeId;
    private int availability;

    public MixerRequestDTO() {}
    public MixerRequestDTO(String name, long capacity, Long productTypeId, int availability) {
        this.name = name;
        this.capacity = capacity;
        this.productTypeId = productTypeId;
        this.availability = availability;
    }
}
