package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "product_type_volume")
public class ProductTypeVolume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ptvid;

    private String name;

    @ManyToOne
    @JoinColumn(name = "ptid", nullable = false)
    private ProductType productType;

    private Long volume;

    @Column(name = "unitprice")
    private BigDecimal unitPrice;

    private String image;

    // FIX: corrected spelling from catergory → category
    private String category;

    @ManyToOne
    @JoinColumn(name = "bottleid", nullable = false)
    private Bottletype bottletype;

    @ManyToOne
    @JoinColumn(name = "labelid", nullable = false)
    private Labeltype labeltype;


    public ProductType getProducttype() {
        return productType;
    }

    public Bottletype getBottle() {
        return bottletype;
    }

    public Labeltype getLabel() {
        return labeltype;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Long getVolume() { return this.volume; }
}