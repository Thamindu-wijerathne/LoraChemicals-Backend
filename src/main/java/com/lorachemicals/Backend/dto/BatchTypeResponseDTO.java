package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BatchTypeResponseDTO {

    private Long batchtypeid;
    private String uniqueBatchCode;
    private Long ptvid;
    private String producttypename;

    private Long boxid;
    private String name;
    private Integer quantityInBox;

    private String batchtypename;

    private String imageURL;

    public BatchTypeResponseDTO() {
    }
}
