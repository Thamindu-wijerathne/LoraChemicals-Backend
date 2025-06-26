package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.AnnouncementDTO;
import com.lorachemicals.Backend.model.Announcement;
import com.lorachemicals.Backend.services.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // ✅ POST: Create new announcement using DTO
    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO savedAnnouncement = announcementService.createAnnouncement(announcementDTO);
        return new ResponseEntity<>(savedAnnouncement, HttpStatus.CREATED);
    }

    // ✅ GET: Retrieve all announcements
    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncement();
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    // ✅ PUT: Update existing announcement by ID
    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable("id") Long announcementId,
            @RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO updatedAnnouncement = announcementService.updateAnnouncement(announcementId, announcementDTO);
        return new ResponseEntity<>(updatedAnnouncement, HttpStatus.OK);
    }

    // ✅ DELETE: Delete announcement by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable("id") Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.noContent().build();
    }
}
