package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryResponseDTO {
    private Long deliveryid;
    private LocalDateTime dispatchdate;
    private LocalDateTime camedate;
    private int status;
    private Long srepid;
    private String salesRepName;
    private Long routeid;
    private String routeName;
    private Long vehicleid;
    private String vehicleNumber;
    
    private List<BatchInventoryDeliveryResponseDTO> batchInventoryItems;
}
