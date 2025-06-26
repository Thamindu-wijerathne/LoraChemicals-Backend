package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bottle")
public class Bottle {
    @Id
    private Long bottleid;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "bottleid",
            referencedColumnName = "bottleid",
            foreignKey = @ForeignKey(name = "fk_bottle", foreignKeyDefinition = "FOREIGN KEY (bottleid) REFERENCES bottle_type(bottleid) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Labeltype labelType;

    @ManyToOne
    @JoinColumn(
            name = "rmtid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_rmtid", foreignKeyDefinition = "FOREIGN KEY (rmtid) REFERENCES raw_material_types(id) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private RawMaterialType rawMaterialType;

    private int quantity;

    // Constructors
    public Bottle() {
    }

    public Bottle(Long bottleid, Labeltype labelType, RawMaterialType rawMaterialType, int quantity) {
        this.bottleid = bottleid;
        this.labelType = labelType;
        this.rawMaterialType = rawMaterialType;
        this.quantity = quantity;
    }

    // Getter and Setter for bottleid
    public Long getBottleid() {
        return bottleid;
    }

    public void setBottleid(Long bottleid) {
        this.bottleid = bottleid;
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

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}