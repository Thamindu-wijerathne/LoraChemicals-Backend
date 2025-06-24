package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "solid_chemical_type")
public class Solid_chemical_type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schemid;
    private String melting_point;

    public Solid_chemical_type(){

    }

    //getters and setters
    public Long getSchemid() {return schemid;}
    public void setSchemid(Long schemid) {this.schemid = schemid;}
    public String getMelting_point() {return melting_point;}
    public void setMelting_point(String melting_point) {this.melting_point = melting_point;}
}
