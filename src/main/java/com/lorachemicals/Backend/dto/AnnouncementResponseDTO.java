package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementResponseDTO {

    private Long announcementID;
    private String message;
    private LocalDateTime dateTime;
    private List<String> targetRoles;

    // No-arg constructor
    public AnnouncementResponseDTO() {
    }

    // All-arg constructor
    public AnnouncementResponseDTO(Long announcementID, String message, LocalDateTime dateTime, List<String> targetRoles) {
        this.announcementID = announcementID;
        this.message = message;
        this.dateTime = dateTime;
        this.targetRoles = targetRoles;
    }

    // Getters and setters
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

    public List<String> getTargetRoles() {
        return targetRoles;
    }

    public void setTargetRoles(List<String> targetRoles) {
        this.targetRoles = targetRoles;
    }
}
