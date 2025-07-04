package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductTypeVolumeRequestDTO {
    private String name;
    private Long unitprice;
    private String image;
    private String category;
    private Long volume;
    private Long bottleid;
    private Long labelid;
    private Long ptid;

    public ProductTypeVolumeRequestDTO(String name, Long unitprice, String category, Long bottleid,  Long volume, Long labelid, Long ptid) {
        this.name = name;
        this.unitprice = unitprice;
        this.category = category;
        this.volume = volume;
        this.bottleid = bottleid;
        this.labelid = labelid;
        this.ptid = ptid;
    }
}
