package com.lorachemicals.Backend.dto;

public class LabeltypeResponseDTO {
    private Long labelid;
    private String name;
    private String volume;

    public LabeltypeResponseDTO(){

    }

    //getters and setyters
    public Long getlabelid() {
        return this.labelid;
    }
    public void setlabelid(Long bottleid) {
        this.labelid = bottleid;
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
}
