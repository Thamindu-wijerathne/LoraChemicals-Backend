package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxRequestDTO {

    @JsonProperty("boxid")
    private Long boxId;

    private int quantity;

    private String location;

    public BoxRequestDTO() {}

    public BoxRequestDTO(Long boxId, int quantity, String location) {
        this.boxId = boxId;
        this.quantity = quantity;
        this.location = location;
    }
}
