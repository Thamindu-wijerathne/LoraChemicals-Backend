package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDTO {
    private LocalDate dispatchDate;
    private LocalDate cameDate;
    private int status;

    private Long srepId;
    private Long vehicleId;
    private Long routeId;

    private Float mileage;
    private Float fuelCost;

}
