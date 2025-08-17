package com.lorachemicals.Backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryid;

    private LocalDateTime dispatchdate;
    private LocalDateTime camedate;
    private int status;

    @ManyToOne
    @JoinColumn(name = "srepid", nullable = false)
    private SalesRep salesRep;

    @ManyToOne
    @JoinColumn(name = "routeid", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "vehicleid", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;

    // Default constructor
    public Delivery() {}

    // Constructor with parameters
    public Delivery(LocalDateTime dispatchdate, LocalDateTime camedate, int status, 
                   SalesRep salesRep, Route route, Vehicle vehicle) {
        this.dispatchdate = dispatchdate;
        this.camedate = camedate;
        this.status = status;
        this.salesRep = salesRep;
        this.route = route;
        this.vehicle = vehicle;
    }
}
