package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "raw_chemical_type")
public class RawChemicalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chemid;

    private String name;      // e.g., "Hydrochloric Acid", "Ammonia"

    private String description;

    private String type;

    public Long getId() {
        return chemid;
    }

    // Optional: description, hazard label, etc.
}
