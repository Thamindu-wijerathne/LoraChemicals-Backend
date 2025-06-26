package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_material")
public class Rawmaterial {
    @Id
    private Long rmtid;

    @ManyToOne
    @JoinColumn(
            name = "rmtid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_rmtid", foreignKeyDefinition = "FOREIGN KEY (rmtid) REFERENCES raw_material_types(id) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private RawMaterialType rawMaterialType;

    @ManyToOne
    @MapsId
    @JoinColumn(
            name = "inventoryid",
            referencedColumnName = "inventoryid",
            foreignKey = @ForeignKey(name = "fk_inventory", foreignKeyDefinition = "FOREIGN KEY (inventoryid) REFERENCES inventory(inventoryid) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Inventory inventory;

    // Constructors
    public Rawmaterial() {
    }

    public Rawmaterial(Long rmtid, RawMaterialType rawMaterialType, Inventory inventory) {
        this.rmtid = rmtid;
        this.rawMaterialType = rawMaterialType;
        this.inventory = inventory;
    }

    // Getter and Setter for rmtid
    public Long getRmtid() {
        return rmtid;
    }

    public void setRmtid(Long rmtid) {
        this.rmtid = rmtid;
    }

    // Getter and Setter for rawMaterialType
    public RawMaterialType getRawMaterialType() {
        return rawMaterialType;
    }

    public void setRawMaterialType(RawMaterialType rawMaterialType) {
        this.rawMaterialType = rawMaterialType;
    }

    // Getter and Setter for inventory
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}