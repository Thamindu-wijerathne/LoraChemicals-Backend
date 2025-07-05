package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchTypeRequestDTO {
    private Long ptvid;
    private Long boxid;

    public BatchTypeRequestDTO() {}
}
