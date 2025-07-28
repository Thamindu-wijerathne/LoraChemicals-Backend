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
    private Long batchtypewithoutboxid;
    private LocalDateTime batchdate;
    private Long wmid;
    private int quantity;
    private Long prodid;
    private String status;
}
