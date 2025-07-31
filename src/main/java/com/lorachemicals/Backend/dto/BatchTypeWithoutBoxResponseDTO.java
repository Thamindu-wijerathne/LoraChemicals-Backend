package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchTypeWithoutBoxResponseDTO {

    private Long batchtypewithoutboxid;
    private String uniqueBatchCode;
    private Long ptvid;
    private String producttypename;
    private String batchtypename;

    public BatchTypeWithoutBoxResponseDTO() {
    }
}
