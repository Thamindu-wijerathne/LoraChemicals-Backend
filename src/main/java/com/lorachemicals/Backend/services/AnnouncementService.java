package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.AnnouncementDTO;
import com.lorachemicals.Backend.model.Announcement;
import com.lorachemicals.Backend.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Announcement createAnnouncement(String message) {
        Announcement announcement = new Announcement();
        announcement.setMessage(message);
        announcement.setDateTime(LocalDateTime.now());
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAllAnnouncement() {
        return announcementRepository.findAll();
    }

    public AnnouncementDTO createAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = new Announcement();
        announcement.setMessage(dto.getMessage());

        announcement.setDateTime(LocalDateTime.now());

        Announcement saved = announcementRepository.save(announcement);

        return new AnnouncementDTO(saved.getAnnouncementID(), saved.getMessage(), saved.getDateTime());
    }

    // Method to update an existing announcement
    public AnnouncementDTO updateAnnouncement(Long announcementId, AnnouncementDTO dto) {
        Announcement announcement = announcementRepository.findById(Math.toIntExact(announcementId))
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        announcement.setMessage(dto.getMessage());
        announcement.setDateTime(LocalDateTime.now());

        Announcement updated = announcementRepository.save(announcement);

        return new AnnouncementDTO(updated.getAnnouncementID(), updated.getMessage(), updated.getDateTime());
    }


    public void deleteAnnouncement(Long announcementId) {
        announcementRepository.deleteById(Math.toIntExact(announcementId));
    }
}
