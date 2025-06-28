package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "raw_chemical")
@PrimaryKeyJoinColumn(name = "inventoryid")
@Getter
@Setter
public class RawChemical extends RawMaterial {

    @ManyToOne
    @JoinColumn(name = "chemid", referencedColumnName = "chemid", nullable = false)
    private RawChemicalType chemicalType;

    private double volume;
}
