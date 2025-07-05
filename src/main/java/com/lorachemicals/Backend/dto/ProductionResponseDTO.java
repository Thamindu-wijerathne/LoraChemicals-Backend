package com.lorachemicals.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionResponseDTO {
    private Long prodid;
    private Long ptid;
    private Long wmid;
    private Long mixerid;
    private Date date;
    private Double volume;
    private Double currentvolume;

}
