package com.lorachemicals.Backend.dto;

import com.lorachemicals.Backend.model.RawMaterialType;
import lombok.Getter;
import lombok.Setter;

@Setter
public class BottleResponseDTO {
    // Getters and Setters
    @Getter
    private Long bottleid;
    @Getter
    private Long labelTypeId;
    @Getter
    private String labelTypeName; // or other identifying field
    private Long rawMaterialTypeId;
    @Getter
    private String rawMaterialTypeName; // or other identifying field
    @Getter
    private int quantity;

    // Constructors
    public BottleResponseDTO() {
    }

    public BottleResponseDTO(Long bottleid, Long labelTypeId, String labelTypeName,
                             Long rawMaterialTypeId, String rawMaterialTypeName, int quantity) {
        this.bottleid = bottleid;
        this.labelTypeId = labelTypeId;
        this.labelTypeName = labelTypeName;
        this.rawMaterialTypeId = rawMaterialTypeId;
        this.rawMaterialTypeName = rawMaterialTypeName;
        this.quantity = quantity;
    }

    public Long getRawMaterialTypeId(RawMaterialType rawMaterialType) {
        return rawMaterialTypeId;
    }

}
