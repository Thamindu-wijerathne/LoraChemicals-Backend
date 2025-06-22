package com.lorachemicals.Backend.dto;

public class InventoryResponseDTO {

    private Long inventoryid;
    private String location;

    public InventoryResponseDTO(){

    }

    public void setID(Long inventoryid){
        this.inventoryid = inventoryid;
    }
    public Long getId(){
        return this.inventoryid;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getLocation(){
        return this.location;
    }
}
