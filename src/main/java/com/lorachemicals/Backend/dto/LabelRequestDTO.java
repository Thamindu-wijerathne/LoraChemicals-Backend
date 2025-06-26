package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelRequestDTO {
    private Long labelId;
    private int quantity;

    public LabelRequestDTO() {}

    public LabelRequestDTO(Long labelId, int quantity) {
        this.labelId = labelId;
        this.quantity = quantity;
    }

    public Long getLabelTypeId() {
        return labelId;
    }
}
