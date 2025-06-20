package com.lorachemicals.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    public Vehicle() {
    }

    public Vehicle(Long id, String vehicleNo, String vehicleType, String capacity) {
        Id = id;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    }

    @Id
    private Long Id;
    private String vehicleNo;
    private String vehicleType;
    private String capacity;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


}
