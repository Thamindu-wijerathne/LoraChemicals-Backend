package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "label_type")
public class Labeltype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labelid;
    private String name;
    private String volume;

    public Labeltype(){

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
