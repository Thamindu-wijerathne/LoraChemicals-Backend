package com.lorachemicals.Backend.dto;

public class VehicleRequestDTO {
    private String vehicleNo;
    private String vehicleType;
    private String capacity;

    public VehicleRequestDTO() {
    }

    public VehicleRequestDTO(String vehicleNo, String vehicleType, String capacity) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
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
