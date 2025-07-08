package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDTO {
    private Long recipeid;
    private String recipeName;
    private Long mixerid;
}
