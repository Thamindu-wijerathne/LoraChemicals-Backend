package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "batch_type")
public class BatchType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchtypeid;

    @ManyToOne
    @JoinColumn(name = "ptvid", nullable = false)
    private ProductTypeVolume productTypeVolume;

    @ManyToOne
    @JoinColumn(name = "boxid", nullable = false)
    private BoxType boxType;

    private String batchtypename;


    public BatchType() {}
}
