package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipe")
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeid;

    private String recipeName;

    @ManyToOne
    @JoinColumn(name = "productTypeId",unique = true, nullable = false)
    private ProductType productType;
}