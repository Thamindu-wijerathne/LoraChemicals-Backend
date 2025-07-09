package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchInventoryRequestDTO {
    @JsonProperty("batchtypeid")
    private Long batchtypeid;

    private int batch_quantity;

    private String location;
}
