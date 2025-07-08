package com.lorachemicals.Backend.dto;

import com.lorachemicals.Backend.model.RawChemicalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeItemRequestDTO {
    private Long recipeid;
    private Long rawchemicalid;
    private String unit;
    private Double quantity;

    public Long getChemid() {
        return rawchemicalid;
    }

}

