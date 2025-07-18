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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementID;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "target_roles") // comma-separated values
    private String targetRoles;

    public Announcement() {}

    public Announcement(Long announcementID, String message, LocalDateTime dateTime, String targetRoles) {
        this.announcementID = announcementID;
        this.message = message;
        this.dateTime = dateTime;
        this.targetRoles = targetRoles;
    }
}
