package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bottle_type")
public class Bottletype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bottleid;
    private String name;
    private String volume;

    public Bottletype(){

    }

    //getters and setyters
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
}
