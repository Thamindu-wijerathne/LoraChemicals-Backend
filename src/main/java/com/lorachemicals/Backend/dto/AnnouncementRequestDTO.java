package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementRequestDTO {
    private String message;
    private LocalDateTime dateTime;
    private List<String> targetRoles;

    // No-arg constructor
    public AnnouncementRequestDTO() {
    }

    // All-arg constructor (without ID)
    public AnnouncementRequestDTO(String message, LocalDateTime dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    // Getters and Setters
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