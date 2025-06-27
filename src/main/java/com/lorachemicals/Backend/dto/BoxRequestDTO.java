package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxRequestDTO {

    @JsonProperty("box_id")
    private Long boxId;

    private int quantity;

    public BoxRequestDTO() {}

    public BoxRequestDTO(Long boxId, int quantity) {
        this.boxId = boxId;
        this.quantity = quantity;
    }
}
