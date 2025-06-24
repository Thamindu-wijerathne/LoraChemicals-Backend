package com.lorachemicals.Backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "ProductType")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productTypeId;

    private String name;

    private String details;

    public ProductType() {
    }

    public ProductType(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
