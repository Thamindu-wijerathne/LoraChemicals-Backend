package com.lorachemicals.Backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BillResponseDTO {
    private Long billid;
    private Long total;
    private Date datetime;
    private Long salesRepId;
    private String salesRepName; // Optional: from linked User object
}
