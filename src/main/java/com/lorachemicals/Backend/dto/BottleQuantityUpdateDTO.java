package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BottleQuantityUpdateDTO {
    private int quantity;
    private String location;
}