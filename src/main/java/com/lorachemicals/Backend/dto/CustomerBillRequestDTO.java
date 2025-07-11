package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBillRequestDTO {
    private String shop_name;
    private String address;
    private String phone;
    private Long billid; // ID of the related Bill
}
