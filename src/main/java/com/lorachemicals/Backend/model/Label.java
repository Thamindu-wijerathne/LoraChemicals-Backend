package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "label")
public class Label {

    @Id
    private Long labelid;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "labelid",
            referencedColumnName = "labelid",
            foreignKey = @ForeignKey(name = "fk_bottleid", foreignKeyDefinition = "FOREIGN KEY (labelid) REFERENCES label_type(labelid) ON DELETE CASCADE ON UPDATE CASCADE")
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
    public Label() {}

    public Label(Long labelid, Labeltype labelType, RawMaterialType rawMaterialType, int quantity) {
        this.labelid = labelid;
        this.labelType = labelType;
        this.rawMaterialType = rawMaterialType;
        this.quantity = quantity;
    }

    // Getter and Setter for labelid
    public Long getLabelid() {
        return labelid;
    }

    public void setLabelid(Long labelid) {
        this.labelid = labelid;
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