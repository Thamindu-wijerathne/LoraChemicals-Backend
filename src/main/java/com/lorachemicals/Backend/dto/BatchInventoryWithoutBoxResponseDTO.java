package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BatchInventoryWithoutBoxResponseDTO {

    @JsonProperty("inventoryid")
    private Long inventoryId;

    @JsonProperty("batchtypewithoutboxid")
    private Long batchtypewithoutboxid;

    private int batch_quantity;

    private String location;
}
