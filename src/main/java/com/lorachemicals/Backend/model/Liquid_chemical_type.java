package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "liquid_chemical_type")
public class Liquid_chemical_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lchemid;
    private String ph_level;

    public Liquid_chemical_type(){

    }
    //getters and setters
    public Long getLchemid() {return lchemid;}
    public void setLchemid(Long lchemid) {this.lchemid = lchemid;}
    public String getPh_level() {return ph_level;}
    public void setPh_level(String ph_level) {this.ph_level = ph_level;}
}
