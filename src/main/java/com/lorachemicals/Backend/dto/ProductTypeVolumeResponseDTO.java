package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeVolumeResponseDTO {
    private Long ptvid;
    private String name;
    private Long ptid;
    private String details;  // <-- add this field
    private String productTypeName;
    private Long volume;
    private Long unitprice;
    private String imageUrl; // Provide URL for frontend to preview/download image
    private String category;
    private Long bottleid;
    private String bottleTypeName;
    private Long labelid;
    private String labelTypeName;

    public ProductTypeVolumeResponseDTO(
            Long ptvid,
            String name,
            Long unitprice,
            String image,
            String catergory,
            Long bottleid,
            Long volume,
            Long labelid,
            Long ptid,
            String details) {
        this.ptvid = ptvid;
        this.name = name;
        this.unitprice = unitprice;
        this.image = image;
        this.catergory = catergory;
        this.bottleid = bottleid;
        this.volume = volume;
        this.labelid = labelid;
        this.ptid = ptid;
        this.details = details;  // <-- set it here
    }

    @Override
    public String toString() {
        return "ProductTypeVolumeResponseDTO{" +
                "ptvid=" + ptvid +
                ", name='" + name + '\'' +
                ", unitprice=" + unitprice +
                ", image='" + image + '\'' +
                ", catergory='" + catergory + '\'' +
                ", volume=" + volume +
                ", bottleid=" + bottleid +
                ", labelid=" + labelid +
                ", ptid=" + ptid +
                ", details='" + details + '\'' +
                '}';
    }
}

