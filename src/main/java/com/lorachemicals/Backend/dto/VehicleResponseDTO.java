package com.lorachemicals.Backend.dto;


public class VehicleResponseDTO {
    private Long id;
    private String vehicleNo;
    private String vehicleType;
    private String capacity;

    public VehicleResponseDTO() {
    }

    public VehicleResponseDTO(Long id, String vehicleNo, String vehicleType, String capacity) {
        this.id = id;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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