package com.lorachemicals.Backend.dto;

public class InventoryRequestDTO {
    private String location;

    public InventoryRequestDTO(){

    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getLocation(){
        return this.location;
    }
}
