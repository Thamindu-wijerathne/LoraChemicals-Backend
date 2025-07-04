package com.lorachemicals.Backend.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class RecipeItemRawChemicalId implements Serializable {

    private Long recipeitemid;
    private Long chemid;

    public RecipeItemRawChemicalId() {}

    public RecipeItemRawChemicalId(Long recipeitemid, Long chemid) {
        this.recipeitemid = recipeitemid;
        this.chemid = chemid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeItemRawChemicalId)) return false;
        RecipeItemRawChemicalId that = (RecipeItemRawChemicalId) o;
        return Objects.equals(recipeitemid, that.recipeitemid) &&
                Objects.equals(chemid, that.chemid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeitemid, chemid);
    }
}
