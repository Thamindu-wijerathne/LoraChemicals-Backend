package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BottleRequestDTO {
    private Long bottleTypeId;
    private int quantity;
    private String location;


    public Long getBottleId() {
        return bottleTypeId;
    }
}
