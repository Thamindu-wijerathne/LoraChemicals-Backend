package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lable")
public class Lable {

    @Id
    private Long labelid;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "labelid",
            referencedColumnName = "labelid",
            foreignKey = @ForeignKey(name = "fk_lableid", foreignKeyDefinition = "FOREIGN KEY (labelid) REFERENCES label_type(labelid) ON DELETE CASCADE ON UPDATE CASCADE")
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

    public Lable() {}

    public Long getLabelid() {
        return labelid;
    }

    public void setLabelid(Long labelid) {
        this.labelid = labelid;
    }

    public Labeltype getLabelType() {
        return labelType;
    }

    public void setLabelType(Labeltype labelType) {
        this.labelType = labelType;
    }

    public RawMaterialType getRawMaterialType() {
        return rawMaterialType;
    }

    public void setRawMaterialType(RawMaterialType rawMaterialType) {
        this.rawMaterialType = rawMaterialType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}