package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItemRequestDTO {
    private Long ptvid;
    private int quantity;
    private Long total;
}
