package com.lorachemicals.Backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {
    private Long announcementID; //correct the typo
    private String message;
    private LocalDateTime dateTime; //date into dateTime

    public LocalDateTime getDate() {
        return null;
    }
}
