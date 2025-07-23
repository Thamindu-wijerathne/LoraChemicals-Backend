package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeliveryRequestDTO {
    private Long deliveryId;
    private LocalDateTime deliveryDate;
    private List<DeliveryInventoryRequestDTO> items;
}
