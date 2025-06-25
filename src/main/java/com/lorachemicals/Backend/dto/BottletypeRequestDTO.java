package com.lorachemicals.Backend.dto;

public class BottletypeRequestDTO {
    private String name;
    private String volume;

    public BottletypeRequestDTO(){

    }

    //getters and setyters
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
        return "BottletypeRequestDTO{" +
                "name='" + name + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

}
