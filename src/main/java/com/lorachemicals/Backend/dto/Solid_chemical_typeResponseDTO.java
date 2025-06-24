package com.lorachemicals.Backend.dto;

public class Solid_chemical_typeResponseDTO {
    private Long schemid;
    private String melting_point;

    public Solid_chemical_typeResponseDTO(){}

    public void setId(Long schemid){
        this.schemid = schemid;
    }
    public Long getId(){
        return this.schemid;
    }
    public void setMelting_point(String melting_point){
        this.melting_point = melting_point;
    }
    public String getMelting_point(){
        return this.melting_point;
    }
}
