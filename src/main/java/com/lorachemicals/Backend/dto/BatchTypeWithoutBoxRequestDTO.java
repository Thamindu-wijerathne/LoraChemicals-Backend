package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchTypeWithoutBoxRequestDTO {

    private Long ptvid;
    private String batchtypename;

    public BatchTypeWithoutBoxRequestDTO() {
    }
}
