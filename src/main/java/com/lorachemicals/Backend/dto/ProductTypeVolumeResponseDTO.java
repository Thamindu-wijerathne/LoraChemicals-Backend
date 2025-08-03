package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal unitprice;
    private String imageUrl; // Provide URL for frontend to preview/download image
    private String category;
    private Long bottleid;
    private String bottleTypeName;
    private Long labelid;
    private String labelTypeName;
    private String productdetails;

    public ProductTypeVolumeResponseDTO(
            Long ptvid,
            String name,
            BigDecimal unitprice,
            Long bottleid,
            Long volume,
            Long labelid,
            Long ptid,
            String details) {
        this.ptvid = ptvid;
        this.name = name;
        this.unitprice = unitprice;
        this.bottleid = bottleid;
        this.volume = volume;
        this.labelid = labelid;
        this.ptid = ptid;
        this.details = details;  // <-- set it here
    }

    public ProductTypeVolumeResponseDTO(
            Long ptvid,
            String name,
            Long ptid,
            String details,
            String productdetails,
            Long volume,
            BigDecimal unitprice,
            String imageUrl,
            String category,
            Long bottleid,
            String bottleTypeName,
            Long labelid,
            String labelTypeName
    ) {
        this.ptvid = ptvid;
        this.name = name;
        this.ptid = ptid;
        this.details = details;
        this.volume = volume;
        this.unitprice = unitprice;
        this.imageUrl = imageUrl;
        this.category = category;
        this.bottleid = bottleid;
        this.bottleTypeName = bottleTypeName;
        this.labelid = labelid;
        this.labelTypeName = labelTypeName;
        this.productdetails = productdetails;
    }



    @Override
    public String toString() {
        return "ProductTypeVolumeResponseDTO{" +
                "ptvid=" + ptvid +
                ", name='" + name + '\'' +
                ", unitprice=" + unitprice +
                ", volume=" + volume +
                ", bottleid=" + bottleid +
                ", labelid=" + labelid +
                ", ptid=" + ptid +
                ", details='" + details + '\'' +
                '}';
    }
}

