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
            foreignKey = @ForeignKey(name = "fk_labelid", foreignKeyDefinition = "FOREIGN KEY (labelid) REFERENCES label_type(labelid) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Labeltype labeltype;

    @ManyToOne
    @JoinColumn(
            name = "rmtid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_rmtid", foreignKeyDefinition = "FOREIGN KEY (rmtid) REFERENCES raw_material_types(id) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private RawMaterialType rawMaterialType;

    private int quantity;

    public Label() {}

    public Long getLabelid() {
        return labelid;
    }

    public void setLabelid(Long labelid) {
        this.labelid = labelid;
    }

    public Labeltype getLabeltype() {
        return labeltype;
    }

    public void setLabeltype(Labeltype labeltype) {
        this.labeltype = labeltype;
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
    @Override
    public String toString() {
        return "Label{" +
                "labelid=" + labelid +
                ", labeltype=" + labeltype +
                ", rawMaterialType=" + rawMaterialType +
                ", quantity=" + quantity +
                '}';
    }
}