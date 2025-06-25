package com.lorachemicals.Backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "rawchemical_type")
public class RawChemicalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chemid;


    public RawChemicalType() {}

    public void setChemid(Long chemid) {
        this.chemid = chemid;
    }

    public Long getChemid() {
        return chemid;
    }
}
