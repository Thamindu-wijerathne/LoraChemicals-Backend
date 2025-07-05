package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrderItemRequestDTO {
    private Long ptvid;
    private int quantity;

    @Override
    public String toString() {
        return "CustomerOrderItemRequestDTO{" +
                "ptvid=" + ptvid +
                ", quantity=" + quantity +
                '}';
    }
}
