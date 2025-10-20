package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class DamageReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate damageDate;
    private String damageItem;
    private String description;
    private String imageUrl;
    private LocalDate reportDate;
    private String sourceType;
    private String status;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser; // link to the User entity
    // Getters and Setters


}

