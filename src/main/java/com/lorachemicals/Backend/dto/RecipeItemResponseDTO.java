package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemResponseDTO {
    private Long recipeitemid;
    private Long recipeid;
    private Long rawchemicalid;
    private String unit;
    private Double quantity;
}
