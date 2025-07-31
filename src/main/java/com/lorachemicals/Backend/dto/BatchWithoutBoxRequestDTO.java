package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchWithoutBoxRequestDTO {

    private Long batchtypewithoutboxid; // Keep for backward compatibility
    private Long parentBatchTypeId; // New field for parent batch type
    private LocalDateTime batchdate;
    private Long wmid;
    private int quantity;
    private Long prodid;
    private String status;

    // Helper method to get the correct batch type ID
    public Long getEffectiveBatchTypeId() {
        return parentBatchTypeId != null ? parentBatchTypeId : batchtypewithoutboxid;
    }
}
