package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequestDTO {
    private String vehicleNo;
    private String vehicleType;
    private String capacity;
    private Long seats;
    private String date;
    private String image;
    private String description;
    private String status;

    public VehicleRequestDTO() {
    }

    public VehicleRequestDTO(String vehicleNo, String vehicleType, String capacity, Long seats, String date, String description, String image, String status) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.seats = seats;
        this.date = date;
        this.image = image;
        this.description = description;
        this.status = status;
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

    @Override
    public String toString() {
        return "VehicleRequestDTO{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", capacity='" + capacity + '\'' +
                ", seats=" + seats +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
