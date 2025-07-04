package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recipe_item")
@Data
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeitemid;

    @ManyToOne
    @JoinColumn(name = "recipeid", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "chemid", nullable = false)
    private RawChemicalType chemical;

    private String unit;

    private Double quantity;

    public void setRawChemical(RawChemicalType chemicalType) {
        this.chemical = chemicalType;
    }

    public RawChemicalType getRawChemical() {
        return chemical;
    }

    public Long getId() {
        return recipeitemid;
    }
}