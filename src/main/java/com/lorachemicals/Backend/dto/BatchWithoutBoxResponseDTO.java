package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchWithoutBoxResponseDTO {

    private Long batchwithoutboxid;
    private Long batchtypewithoutboxid; // Keep for backward compatibility
    private Long parentBatchTypeId;
    private String uniqueBatchCode;
    private String batchtypename;
    private LocalDateTime batchdate;
    private Long wmid;
    private String warehouseManagerName;
    private int quantity;
    private Long prodid;
    private String productionStatus;
    private String status;
}
