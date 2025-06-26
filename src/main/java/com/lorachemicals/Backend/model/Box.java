package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "box")
public class Box {

    @Id
    private Long boxid;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "boxid",
            referencedColumnName = "boxid",
            foreignKey = @ForeignKey(name = "fk_box_box_type", foreignKeyDefinition = "FOREIGN KEY (boxid) REFERENCES box_type(boxid) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private BoxType boxType;

    @ManyToOne
    @JoinColumn(
            name = "rmtid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_rmtid", foreignKeyDefinition = "FOREIGN KEY (rmtid) REFERENCES raw_material_types(id) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private RawMaterialType rawMaterialType;

    private int quantity;

    // Constructors
    public Box() {}

    public Long getBoxid() {
        return boxid;
    }

    public void setBoxid(Long boxid) {
        this.boxid = boxid;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
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