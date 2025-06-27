package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bottle")
@PrimaryKeyJoinColumn(name = "inventoryid")
@Getter
@Setter
public class Bottle extends RawMaterial {

    @ManyToOne
    @JoinColumn(name = "bottleid", nullable = false)
    private Bottletype bottleType;

    private int quantity;
}