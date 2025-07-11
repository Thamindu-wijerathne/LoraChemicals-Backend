package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "damaged_resource")
public class DamagedResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batchid", nullable = false)
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "bottleid", nullable = true)
    private Bottletype bottletype;

    @ManyToOne
    @JoinColumn(name = "labelid", nullable = true)
    private Labeltype labeltype;

    @ManyToOne
    @JoinColumn(name = "prodid", nullable = true)
    private Production production;

    @ManyToOne
    @JoinColumn(name = "boxid", nullable = true)
    private BoxType boxType;

    private int quantity;

    @Column(nullable = false)
    private String evidenceDescription;

    private LocalDateTime reportedAt;
}
