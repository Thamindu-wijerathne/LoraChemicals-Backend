package com.lorachemicals.Backend.dto;

public class BottletypeResponseDTO {
    private Long bottleid;
    private String name;
    private String volume;

    public BottletypeResponseDTO(){

    }

    //getters and setters
    public Long getBottleid() {
        return this.bottleid;
    }
    public void setBottleid(Long bottleid) {
        this.bottleid = bottleid;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getVolume() {
        return this.volume;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "BottletypeResponseDTO{" +
                "name='" + name + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

}
