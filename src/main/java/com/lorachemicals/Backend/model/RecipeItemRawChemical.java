package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipeitem_rawchemical")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeItemRawChemical {

    @EmbeddedId
    private RecipeItemRawChemicalId id;

    @ManyToOne
    @MapsId("recipeitemid")
    @JoinColumn(name = "recipeitemid")
    private RecipeItem recipeItem;

    @ManyToOne
    @MapsId("chemid")
    @JoinColumn(name = "chemid")
    private RawChemicalType rawChemicalType;

    public void setRawChemical(RawChemicalType chemical) {
        this.rawChemicalType = chemical;
        if (this.id == null) {
            this.id = new RecipeItemRawChemicalId();
        }
        if (chemical != null) {
            this.id.setChemid(chemical.getId());
        }
    }

    public void setRecipeItem(RecipeItem recipeItem) {
        this.recipeItem = recipeItem;
        if (this.id == null) {
            this.id = new RecipeItemRawChemicalId();
        }
        if (recipeItem != null) {
            this.id.setRecipeitemid(recipeItem.getId());
        }
    }
}