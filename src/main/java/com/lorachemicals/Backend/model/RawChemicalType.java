package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_chemical_type")
public class RawChemicalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <-- FIXED HERE
    private Long chemid;

    private String name;
    private String type;
    private String description;

    public RawChemicalType() {}

    public Long getChemid() {
        return chemid;
    }

    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}