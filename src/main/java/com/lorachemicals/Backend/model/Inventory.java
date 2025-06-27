package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryid;

    private String location;

    public Object getId() {
        return inventoryid;
    }

    // other fields, getters/setters
}