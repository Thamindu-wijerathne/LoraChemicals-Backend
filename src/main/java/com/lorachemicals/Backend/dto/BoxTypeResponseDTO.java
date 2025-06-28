package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxTypeResponseDTO {

    private Long boxid;

    @JsonProperty("quantity_in_box")
    private int quantityInBox;

    private String name;

    public BoxTypeResponseDTO() {}
}
