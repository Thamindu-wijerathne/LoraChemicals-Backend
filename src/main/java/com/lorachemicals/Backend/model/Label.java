package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "label")
@PrimaryKeyJoinColumn(name = "inventoryid")
@Getter
@Setter
public class Label extends RawMaterial{

    @ManyToOne
    @JoinColumn(name = "labelid", nullable = false)
    private Labeltype labeltype;

    private int quantity;
}