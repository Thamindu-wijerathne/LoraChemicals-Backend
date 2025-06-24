package com.lorachemicals.Backend.dto;

public class Liquid_chemical_typeRequestDTO {
    private String ph_level;

    public Liquid_chemical_typeRequestDTO(){}

    public void setPh_level(String ph_level){
        this.ph_level = ph_level;
    }
    public String getPh_level(){
        return this.ph_level;
    }
}
