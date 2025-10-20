package com.lorachemicals.Backend.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class CustomerOrderResponseDTO {
    private Long orderid;
    private String ordercode;  // Add order code field
    private Date deliveredDate;
    private String status;
    private BigDecimal total;
    private Long customerId;
    private String customerName;  // optional extra for UI display
    private String route;
    private Long routeid;
    private String feedback;
    private BigDecimal rate;
    private Date orderedDate;
    private String address;


    private List<CustomerOrderItemResponseDTO> items; // ✅ new


}
