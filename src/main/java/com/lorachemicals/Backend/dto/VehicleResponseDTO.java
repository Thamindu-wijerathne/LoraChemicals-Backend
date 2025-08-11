package com.lorachemicals.Backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponseDTO {
    private Long id;
    private String vehicleNo;
    private String vehicleType;
    private String capacity;
    private String date;
    private Long seats;
    private String image;
    private String status;

    public VehicleResponseDTO() {
    }

    public VehicleResponseDTO(Long id, String vehicleNo, String vehicleType, String capacity, Long seats, String date, String image, String status) {
        this.id = id;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.seats = seats;
        this.date = date;
        this.image = image;
        this.status = status;
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

    public void setDescription(String description) {
    }

    @Override
    public String toString() {
        return "VehicleResponseDTO{" +
                "id=" + id +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", capacity='" + capacity + '\'' +
                ", date='" + date + '\'' +
                ", seats=" + seats +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}