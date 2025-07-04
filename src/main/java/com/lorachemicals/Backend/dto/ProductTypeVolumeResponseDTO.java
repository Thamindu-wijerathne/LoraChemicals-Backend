package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductTypeVolumeResponseDTO {
    private Long ptvid;
    private String name;
    private Long unitprice;
    private String image;
    private String category;
    private Long volume;
    private Long bottleid;
    private Long labelid;
    private Long ptid;

    public ProductTypeVolumeResponseDTO(
            Long ptvid,
            String name,
            Long unitprice,
            String image,
            String category,
            Long bottleid,
            Long volume,
            Long labelid,
            Long ptid
    ) {
        this.ptvid = ptvid;
        this.name = name;
        this.unitprice = unitprice;
        this.image = image;
        this.category = category;
        this.bottleid = bottleid;
        this.volume = volume;
        this.labelid = labelid;
        this.ptid = ptid;
    }


}
