package com.lorachemicals.Backend.dto;

public class Solid_chemical_typeRequestDTO {
    private String melting_point;

    public Solid_chemical_typeRequestDTO(){}

    public void setMelting_point(String melting_point){
        this.melting_point = melting_point;
    }
    public String getMelting_point(){
        return this.melting_point;
    }
}
