package com.lorachemicals.Backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductTypeVolumeRequestDTO {
    private Long ptid;
    private String name;
    private Long volume;
    private Long unitprice;
    private String category;
    private Long bottleid;
    private Long labelid;

}