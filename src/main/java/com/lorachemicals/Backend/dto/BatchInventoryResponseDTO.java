package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BatchInventoryResponseDTO {

    @JsonProperty("inventoryid")
    private Long inventoryId;

    @JsonProperty("batchtypeid")
    private Long batchtypeid;

    private int batch_quantity;

    private String location;
}
