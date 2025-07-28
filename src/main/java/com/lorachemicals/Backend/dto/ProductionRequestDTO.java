package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionRequestDTO {
    private Long ptid;
    private Long wmid;
    private Long mixerid;
    private Date date;
    private Double volume;
    private Double currentvolume;
    private String status;
    private Date expiredate;
    private Long recipeid;

    private List<RecipeItemDTO> recipeItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecipeItemDTO {
        private Long recipeItemId;
        private Double quantity;  // or Integer if always integer values
        private String unit;
        private Long chemicalId;
    }
}
