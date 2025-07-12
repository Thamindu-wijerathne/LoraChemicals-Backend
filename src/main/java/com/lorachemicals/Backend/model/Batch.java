package com.lorachemicals.Backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchid;

    @ManyToOne
    @JoinColumn(name = "batchtypeid", nullable = false)
    private BatchType batchtype;

    private LocalDateTime batchdate;

    @ManyToOne
    @JoinColumn(name = "inventoryid" , nullable = false)
    private Box box;

    @ManyToOne
    @JoinColumn(name = "wmid", nullable = false)
    private WarehouseManager warehousemanager;

    @ManyToOne
    @JoinColumn(name = "prodid", nullable = false)
    private Production production;

    private String status;

    private int quantity;

}
