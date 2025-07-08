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

    @OneToOne
    @JoinColumn(name = "mixerid", unique = true, nullable = false)
    private Mixer mixer;
}