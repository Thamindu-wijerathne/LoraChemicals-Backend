package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelRequestDTO {
    private Long labelTypeId;
    private int quantity;
    private String location;

}
