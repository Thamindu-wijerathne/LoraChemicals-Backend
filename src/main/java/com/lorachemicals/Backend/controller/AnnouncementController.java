package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.AnnouncementRequestDTO;
import com.lorachemicals.Backend.dto.AnnouncementResponseDTO;
import com.lorachemicals.Backend.services.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
@CrossOrigin(origins = "**")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // Create a new announcement
    @PostMapping
    public ResponseEntity<AnnouncementResponseDTO> createAnnouncement(@RequestBody AnnouncementRequestDTO requestDTO) {
        AnnouncementResponseDTO created = announcementService.createAnnouncement(requestDTO);
        return ResponseEntity.ok(created);
    }

    // Get all announcements (for admin only)
    @GetMapping
    public ResponseEntity<List<AnnouncementResponseDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    // Get a specific announcement by ID
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> getAnnouncementById(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncement(id));
    }

    // Update an announcement
    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody AnnouncementRequestDTO requestDTO) {
        return ResponseEntity.ok(announcementService.updateAnnouncement(id, requestDTO));
    }

    // Delete an announcement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
