package com.lorachemicals.Backend.dto;

import lombok.Getter;

@Getter
public class RecipeItemCreateRequest {
    private RecipeItemRequestDTO recipeItemDTO;
    private RecipeItemRawChemicalRequestDTO rawChemicalDTO;

}
