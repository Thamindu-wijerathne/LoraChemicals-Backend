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
public class BatchInventoryWithoutBoxRequestDTO {

    @JsonProperty("batchtypewithoutboxid")
    private Long batchtypewithoutboxid;

    private int batch_quantity;

    private String location;
}
