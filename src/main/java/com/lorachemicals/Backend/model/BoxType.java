package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "box_type")
public class BoxType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxid;
    private int capacity;
    private String name;

    public BoxType() {}

    public void setBoxid(Long boxid) {
        this.boxid = boxid;
    }

    public Long getBoxid() {
        return boxid;
    }

    public void setcapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getcapacity() {
        return capacity;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

}
