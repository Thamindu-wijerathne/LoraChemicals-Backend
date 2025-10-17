package com.lorachemicals.Backend.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillResponseDTO {
    private Long billid;
    private Long total;
    private Date datetime;
    private Long salesRepId;
    private String salesRepName; // Optional: from linked User object
    private List<BillItemResponseDTO> billItems;
    
    // Customer information
    private String shopName;
    private String address;
    private String phone;
    private String district;
    
    // Delivery information
    private Long deliveryId;
    private String deliveryStatus;


}


