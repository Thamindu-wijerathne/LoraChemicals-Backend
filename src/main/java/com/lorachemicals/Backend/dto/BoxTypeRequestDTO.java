package com.lorachemicals.Backend.dto;

public class BoxTypeRequestDTO {
    private int capacity;
    private String name;

    public BoxTypeRequestDTO() {}

    public void setcapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getcapacity() {
        return capacity;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

}
