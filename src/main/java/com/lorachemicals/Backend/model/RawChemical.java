package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_chemic")
public class RawChemical {
    @Id
    private Long chemid;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "chemid",
            referencedColumnName = "chemid",
            foreignKey = @ForeignKey(name = "fk_bottle", foreignKeyDefinition = "FOREIGN KEY (chemid) REFERENCES raw_chemical_type(chemid) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Labeltype labelType;

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
    public RawChemical() {
    }

    public RawChemical(Long chemid, Labeltype labelType, RawMaterialType rawMaterialType, String type, String volume) {
        this.chemid = chemid;
        this.labelType = labelType;
        this.rawMaterialType = rawMaterialType;
        this.type = type;
        this.volume = volume;
    }

    // Getter and Setter for chemid
    public Long getChemid() {
        return chemid;
    }

    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }

    // Getter and Setter for labelType
    public Labeltype getLabelType() {
        return labelType;
    }

    public void setLabelType(Labeltype labelType) {
        this.labelType = labelType;
    }

    // Getter and Setter for rawMaterialType
    public RawMaterialType getRawMaterialType() {
        return rawMaterialType;
    }

    public void setRawMaterialType(RawMaterialType rawMaterialType) {
        this.rawMaterialType = rawMaterialType;
    }

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and Setter for volume
    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}