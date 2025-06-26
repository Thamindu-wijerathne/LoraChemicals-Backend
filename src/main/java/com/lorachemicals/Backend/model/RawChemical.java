package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_chemical")
public class RawChemical {
    @Id
    private Long chemid;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "chemid",
            referencedColumnName = "chemid",
            foreignKey = @ForeignKey(name = "fk_chemicalid", foreignKeyDefinition = "FOREIGN KEY (chemid) REFERENCES raw_chemical_type(chemid) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private RawChemicalType chemicalType;

    @ManyToOne
    @JoinColumn(
            name = "rmtid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_rmtid", foreignKeyDefinition = "FOREIGN KEY (rmtid) REFERENCES raw_material_types(id) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private RawMaterialType rawMaterialType;

    private String type;
    private String volume;

    // Constructors
    public RawChemical() {}

    // Getters and Setters

    public Long getChemid() {
        return chemid;
    }

    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }

    public RawChemicalType getChemicalType() {
        return chemicalType;
    }

    public void setChemicalType(RawChemicalType chemicalType) {
        this.chemicalType = chemicalType;
    }

    public RawMaterialType getRawMaterialType() {
        return rawMaterialType;
    }

    public void setRawMaterialType(RawMaterialType rawMaterialType) {
        this.rawMaterialType = rawMaterialType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}