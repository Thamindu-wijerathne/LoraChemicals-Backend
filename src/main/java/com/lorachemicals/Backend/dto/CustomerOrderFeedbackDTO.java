package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderFeedbackDTO {
    private Date deliveredDate;
    private String feedback;
    private BigDecimal rate;
    private String status;
}
