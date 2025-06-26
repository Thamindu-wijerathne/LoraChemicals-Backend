package com.lorachemicals.Backend.dto;

import lombok.Getter;

@Getter
public class BottleRequestDTO {
    // Getters and Setters
    private Long bottleid;
    private Long labelTypeId;
    private Long rawMaterialTypeId;
    private int quantity;

    // Constructors
    public BottleRequestDTO() {
    }

    public BottleRequestDTO(Long bottleid, Long labelTypeId, Long rawMaterialTypeId, int quantity) {
        this.bottleid = bottleid;
        this.labelTypeId = labelTypeId;
        this.rawMaterialTypeId = rawMaterialTypeId;
        this.quantity = quantity;
    }

    public void setBottleid(Long bottleid) {
        this.bottleid = bottleid;
    }

    public void setLabelTypeId(Long labelTypeId) {
        this.labelTypeId = labelTypeId;
    }

    public void setRawMaterialTypeId(Long rawMaterialTypeId) {
        this.rawMaterialTypeId = rawMaterialTypeId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
