package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;

public class AnnouncementDTO {
    private Long announcementID;
    private String message;
    private LocalDateTime dateTime;

    // ✅ No-arg constructor (needed by Spring/Jackson)
    public AnnouncementDTO() {
    }

    // ✅ All-arg constructor
    public AnnouncementDTO(Long announcementID, String message, LocalDateTime dateTime) {
        this.announcementID = announcementID;
        this.message = message;
        this.dateTime = dateTime;
    }

    // ✅ Getters and Setters
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
