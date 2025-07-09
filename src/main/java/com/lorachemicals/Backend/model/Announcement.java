package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "announcement")
public class Announcement {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementID;

    @Column(nullable = false)
    private String message; // cannot exceed more than 255 letters (this should be fixed)

    @Column(nullable = false)
    private LocalDateTime dateTime;

    // No-arg constructor
    public Announcement() {
    }

    // All-args constructor (optional, if you need it)
    public Announcement(Long announcementID, String message, LocalDateTime dateTime) {
        this.announcementID = announcementID;
        this.message = message;
        this.dateTime = dateTime;
    }

}
