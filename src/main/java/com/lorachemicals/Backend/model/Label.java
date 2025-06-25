package com.lorachemicals.Backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "label")
@Getter
@Setter
public class Label extends RawMaterial {

    @OneToOne
    @MapsId // Uses inherited id as both PK and FK
    @JoinColumn(name = "labelid", nullable = false)
    private Labeltype labeltype;

    private Long quantity;
}

