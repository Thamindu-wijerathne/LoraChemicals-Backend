package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CustomerOrderRequestDTO {
    private Date deliveredDate;
    private String status;
    private BigDecimal total;
    private Long customerId;
    private List<CustomerOrderItemRequestDTO> orderItems;

    @Override
    public String toString() {
        return "CustomerOrderRequestDTO{" +
                "deliveredDate=" + deliveredDate +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", customerId=" + customerId +
                ", orderItems=" + orderItems +
                '}';
    }
}
