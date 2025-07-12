package com.lorachemicals.Backend.dto;

import com.lorachemicals.Backend.model.Mixer;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.WarehouseManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDetailedResponseDTO {
    private Long prodid;
    private ProductType producttype;           // Full object
    private WarehouseManager warehousemanager; // Full object
    private Mixer mixer;                       // Full object
    private Date date;
    private Double volume;
    private String status;
    private Double currentvolume;
    private Date expiredate;
}

