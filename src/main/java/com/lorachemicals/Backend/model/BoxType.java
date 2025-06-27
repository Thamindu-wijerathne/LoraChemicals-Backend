package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "box_type")
public class BoxType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxid;
    private String name;

    @Column(name = "quantity_in_box")
    private int quantityInBox;

    public BoxType() {}
}
