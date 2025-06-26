package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementID;

    @Column(nullable = false)
    private String message;

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

    // Getters and Setters
    public Long getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(Long announcementID) {
        this.announcementID = announcementID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
