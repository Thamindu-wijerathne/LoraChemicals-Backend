package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "raw_material")
@PrimaryKeyJoinColumn(name = "inventoryid")
@Getter
@Setter
public class RawMaterial extends Inventory {
    // No extra fields, inherits inventoryid from Inventory
}
