package com.lorachemicals.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchInventoryRequestDTO {

    @JsonProperty("batchtypeid")
    private Long batchtypeid; // Keep for backward compatibility

    @JsonProperty("parentBatchTypeId")
    private Long parentBatchTypeId; // New field for parent batch type

    private int batch_quantity;

    private String location;

    // Helper method to get the correct batch type ID
    public Long getEffectiveBatchTypeId() {
        return parentBatchTypeId != null ? parentBatchTypeId : batchtypeid;
    }
}
