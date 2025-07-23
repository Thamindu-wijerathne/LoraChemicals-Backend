package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDTO {
    private Long deliveryid;
    private LocalDate dispatchdate;
    private LocalDate camedate;
    private int status;

    private String salesRepName;
    private String vehicleNumber;
    private String routeName;

    private Float mileage;
    private Float fuelCost;

    // Getters and Setters (or use Lombok if allowed)
    // ... (generate with your IDE)
}
