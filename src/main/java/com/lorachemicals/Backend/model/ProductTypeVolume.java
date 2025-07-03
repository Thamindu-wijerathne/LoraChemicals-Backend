package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "product_type_volume")
public class ProductTypeVolume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ptvid;

    @ManyToOne
    @JoinColumn(name = "ptid",nullable = false)
    private ProductType productType;

    private String volume;

    private String unitprice;

    private String image;

    @ManyToOne
    @JoinColumn(name = "bottleid", nullable = false)
    private Bottletype bottletype;

    @ManyToOne
    @JoinColumn(name = "labelid", nullable = false)
    private Labeltype labeltype;


}
