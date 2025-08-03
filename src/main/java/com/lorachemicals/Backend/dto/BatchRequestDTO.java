package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequestDTO {

    private Long batchtypeid; // Keep for backward compatibility
    private Long parentBatchTypeId; // New field for parent batch type
    private LocalDateTime batchdate;

    //box
    private Long inventoryid;

    private Long wmid;

    private int quantity;

    private Long prodid;

    private String status;

    // Helper method to get the correct batch type ID
    public Long getEffectiveBatchTypeId() {
        return parentBatchTypeId != null ? parentBatchTypeId : batchtypeid;
    }
}
