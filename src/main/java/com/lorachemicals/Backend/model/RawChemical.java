package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_chemical")
public class RawChemical extends RawMaterial {

    @OneToOne
    @MapsId // Maps inherited id to chemicalType
    @JoinColumn(name = "chemid", nullable = false)
    private RawChemicalType chemicalType;

    private String type;
    private float volume;

    public RawChemicalType getChemicalType() {
        return chemicalType;
    }

    public void setChemicalType(RawChemicalType chemicalType) {
        this.chemicalType = chemicalType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
