package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchResponseDTO {

    private Long batchid;

    private Long batchtypeid;
    private LocalDateTime batchdate;

    //box
    private Long inventoryid;

    private Long wmid;

    private int quantity;

    private Long prodid;

    private String status;

}
