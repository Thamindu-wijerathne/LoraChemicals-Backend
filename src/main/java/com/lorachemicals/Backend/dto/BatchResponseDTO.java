package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchResponseDTO {

    private Long batchid;
    private String batchcode;

    private Long batchtypeid; // Keep for backward compatibility
    private Long parentBatchTypeId;
    private String T;
    private String batchtypename;
    private LocalDateTime batchdate;

    //box
    private Long inventoryid;
    private String boxTypeName;

    private Long wmid;
    private String warehouseManagerName;

    private int quantity;

    private Long prodid;
    private String productionStatus;
    private Date expiredate;

    private String status;
}
