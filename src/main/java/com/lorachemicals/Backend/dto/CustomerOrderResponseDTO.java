package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CustomerOrderResponseDTO {
    private Long orderid;
    private Date deliveredDate;
    private String status;
    private BigDecimal total;
    private Long customerId;
    private String customerName;  // optional extra for UI display
}
