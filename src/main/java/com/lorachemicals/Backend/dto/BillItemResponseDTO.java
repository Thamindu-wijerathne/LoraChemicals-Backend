package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillItemResponseDTO {
    private Long billitemid;
    private Long total;
    private Long quantity;
    private Long ptvid;
    private String ptvName;
    private Long volume;
    private BigDecimal unitPrice;
    private String image;
}
