package com.lorachemicals.Backend.dto;

public class Liquid_chemical_typeResponseDTO {
    private Long lchemid;
    private String ph_level;

    public Liquid_chemical_typeResponseDTO(){}

    public void setId(Long lchemid){
        this.lchemid = lchemid;
    }
    public Long getId(){
        return this.lchemid;
    }

    public void setPh_level(String ph_level){
        this.ph_level = ph_level;
    }
    public String getPh_level(){
        return this.ph_level;
    }
}
