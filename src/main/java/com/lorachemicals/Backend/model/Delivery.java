package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryid;

    private Date dispatchdate;
    private Date camedate;
    private int status;

    @ManyToOne
    @JoinColumn(name = "srepid", nullable = false)
    private SalesRep srep;

    @ManyToOne
    @JoinColumn(name = "vehicleid", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "routeid", nullable = false)
    private Route route;

    private Float mileage;

    private Float fuelcost;
}
