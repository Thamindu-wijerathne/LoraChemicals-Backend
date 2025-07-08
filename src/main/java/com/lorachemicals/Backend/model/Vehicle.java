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

    public Vehicle(String vehicleNo, String vehicleType, String capacity) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    }

}