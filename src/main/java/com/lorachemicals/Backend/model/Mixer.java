package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mixer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mixer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mixerid;

    private String name;

    private long capacity;

    @ManyToOne
    @JoinColumn(name = "productTypeId", nullable = false)
    private ProductType productType;

    private int availability;
}