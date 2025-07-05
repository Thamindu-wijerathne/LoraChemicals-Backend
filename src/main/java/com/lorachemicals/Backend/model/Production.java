package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "production")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodid;

    @ManyToOne
    @JoinColumn(name = "ptid", nullable = false)
    private ProductType productype;

    @ManyToOne
    @JoinColumn(name = "wmid", nullable = false)
    private WarehouseManager warehousemanager;

    @ManyToOne
    @JoinColumn(name = "mixerid", nullable = false)
    private Mixer mixer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Double volume;

    private Double currentvolume;

}
