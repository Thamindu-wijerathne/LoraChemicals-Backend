package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "box")
@PrimaryKeyJoinColumn(name = "inventoryid")
@Getter
@Setter
public class Box extends RawMaterial {

    @ManyToOne
    @JoinColumn(name = "boxid", nullable = false)
    private BoxType boxType;

    private int quantity;
}