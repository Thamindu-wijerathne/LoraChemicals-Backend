package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponseDTO {

    private Long inventoryid;

    private String location;

    public InventoryResponseDTO(){}

    public InventoryResponseDTO(Long inventoryid, String location) {
        this.inventoryid = inventoryid;
        this.location = location;
    }

    public void setID(Object id) {
        this.inventoryid = (Long) id;
    }
}
