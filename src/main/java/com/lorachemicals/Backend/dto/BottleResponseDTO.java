package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BottleResponseDTO {

    private Long inventoryId;
    private Long bottleId;
    private int quantity;
    private String location;
}
