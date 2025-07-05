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
    private String productTypeName;
    private Long volume;
    private Long unitprice;
    private String imageUrl; // Provide URL for frontend to preview/download image
    private String category;
    private Long bottleid;
    private String bottleTypeName;
    private Long labelid;
    private String labelTypeName;
}