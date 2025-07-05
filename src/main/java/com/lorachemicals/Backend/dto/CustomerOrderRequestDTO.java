package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerOrderRequestDTO {
    private Date deliveredDate;
    private String status;
    private Long total;
    private Long customerId;  // Refers to User.id
}
