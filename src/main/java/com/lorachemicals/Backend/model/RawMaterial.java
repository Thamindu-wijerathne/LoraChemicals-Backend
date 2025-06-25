package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "raw_material")
public class RawMaterial {

    @Id
    private Long rmtid;

    @OneToOne
    @MapsId // This tells JPA that this entity shares its PK with the FK
    @JoinColumn(name = "rmtid")
    private RawMaterialType rawMaterialType;

    @OneToOne
    @JoinColumn(name = "inventoryid")
    private Inventory inventory;

    private int quantity;
    private LocalDate receivedDate;

}

