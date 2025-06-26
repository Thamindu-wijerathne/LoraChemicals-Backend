package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoxResponseDTO {

    @JsonProperty("inventory_id")
    private final Long inventoryId;

    @JsonProperty("box_id")
    private final Long boxId;

    private final int quantity;
}
