package com.lorachemicals.Backend.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderFeedbackDTO {
    private String ordercode;
    private Date deliveredDate;
    private String feedback;
    private BigDecimal rate;
    private String status;
}
