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



}
