package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerOrderResponseDTO {
    private Long orderId;
    private Date deliveredDate;
    private String status;
    private Long total;
    private Long customerId;
    private String customerName;  // optional extra for UI display
}
