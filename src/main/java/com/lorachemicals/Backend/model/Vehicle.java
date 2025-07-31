package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String vehicleNo;

    private String vehicleType;
    private String capacity;
    private String description;
    private String image;
    private Long seats;
    private String date;
    private String status;

    public Vehicle(String vehicleNo, String vehicleType, String capacity, String description, String image, Long seats, String date, String status) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.description = description;
        this.image = image;
        this.seats = seats;
        this.date = date;
        this.status = status;
    }


}