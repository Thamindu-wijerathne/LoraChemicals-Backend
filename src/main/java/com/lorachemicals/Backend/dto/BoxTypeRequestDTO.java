
package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxTypeRequestDTO {

    @JsonProperty("quantity_in_box")
    private Integer quantityInBox;

    private String name;

    public BoxTypeRequestDTO() {}
}
