package com.lorachemicals.Backend.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerOrderItemResponseDTO {
    private Long ptvid;
    private int quantity;
    private BigDecimal productTotal;

    @Override
    public String toString() {
        return "CustomerOrderItemRequestDTO{" +
                "ptvid=" + ptvid +
                ", quantity=" + quantity +
                ", productTotal=" + productTotal +
                '}';
    }
}
