package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerOrderItemResponseDTO {
    private Long ptvid;
    private String name;                // Product name
    private int quantity;
    private BigDecimal unitPrice;      // Optional: if needed
    private BigDecimal productTotal;
    private String image;
    private String productTypeName;

    private String bottleTypeName;     // Optional: add if useful for UI
    private String labelTypeName;      // Optional: add if useful for UI
    private Long volume;               // Optional: display volume like 500ml

    @Override
    public String toString() {
        return "CustomerOrderItemResponseDTO{" +
                "ptvid=" + ptvid +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", productTotal=" + productTotal +
                ", image='" + image + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", bottleTypeName='" + bottleTypeName + '\'' +
                ", labelTypeName='" + labelTypeName + '\'' +
                ", volume=" + volume +
                '}';
    }
}
