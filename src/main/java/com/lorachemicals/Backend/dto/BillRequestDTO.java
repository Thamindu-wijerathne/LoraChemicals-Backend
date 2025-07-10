package com.lorachemicals.Backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BillRequestDTO {
    private Long total;
    private Date datetime;
    private Long salesRepId; // the ID of the SalesRep creating this bill

    // Add these for CustomerBill
    private String shop_name;
    private String address;
    private String phone;
}
