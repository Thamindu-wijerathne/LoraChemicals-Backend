package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesRepResponseDTO {
    private Long srepid;
    private Long userid;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String address;
    private String nic;
    private int status;
    private String userStatus;
}
