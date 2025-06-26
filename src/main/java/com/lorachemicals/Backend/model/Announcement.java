package com.lorachemicals.Backend.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name="announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementID;

    @Column (nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateTime;
}