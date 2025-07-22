package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBillResponseDTO {
    private Long cbillid;
    private String shop_name;
    private String address;
    private String phone;
    private Long billid;
    private Long salesRepId;
    private String salesRepName;
    private Long total;
    private String datetime; // or use `LocalDateTime` if preferred
}
