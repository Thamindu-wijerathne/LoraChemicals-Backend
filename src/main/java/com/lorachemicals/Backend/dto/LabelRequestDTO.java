package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelRequestDTO {
    private Long labelId;
    private int quantity;
    private String location;

    public LabelRequestDTO() {}

    public Long getLabelTypeId() {
        return labelId;
    }
}
