package com.lorachemicals.Backend.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
    private String district;
    private Long deliveryid; // Add delivery ID field
    private List<BillItemRequestDTO> items; // New field


    @Override
    public String toString() {
        return "BillRequestDTO{" +
                "total=" + total +
                ", datetime=" + datetime +
                ", salesRepId=" + salesRepId +
                ", shop_name='" + shop_name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", district='" + district + '\'' +
                ", deliveryid=" + deliveryid +
                ", items=" + items +
                '}';
    }

}
