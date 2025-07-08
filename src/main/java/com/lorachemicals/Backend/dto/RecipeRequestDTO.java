package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class RecipeRequestDTO {
    private String recipeName;
    private Long mixerid;
}
