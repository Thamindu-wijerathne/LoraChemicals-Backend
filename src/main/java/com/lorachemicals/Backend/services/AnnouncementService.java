package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.AnnouncementRequestDTO;
import com.lorachemicals.Backend.dto.AnnouncementResponseDTO;
import com.lorachemicals.Backend.model.Announcement;
import com.lorachemicals.Backend.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    // List of all roles in the system
    private static final List<String> ALL_ROLES = List.of("admin", "customer", "warehouse", "rep");

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    // Create a new announcement
    public AnnouncementResponseDTO createAnnouncement(AnnouncementRequestDTO requestDTO) {
        Announcement announcement = new Announcement();
        announcement.setDateTime(LocalDateTime.now());
        announcement.setMessage(requestDTO.getMessage());

        List<String> roles = requestDTO.getTargetRoles();
        if (roles != null && roles.contains("ALL")) {
            announcement.setTargetRoles(String.join(",", ALL_ROLES));
        } else if (roles != null && !roles.isEmpty()) {
            announcement.setTargetRoles(String.join(",", roles));
        } else {
            announcement.setTargetRoles(""); // No roles provided
        }

        Announcement saved = announcementRepository.save(announcement);
        return convertToResponseDTO(saved);
    }

    // Get a single announcement by ID
    public AnnouncementResponseDTO getAnnouncement(Long id) {
        Announcement a = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found with id: " + id));
        return convertToResponseDTO(a);
    }

    // Get all announcements
    public List<AnnouncementResponseDTO> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update an existing announcement
    public AnnouncementResponseDTO updateAnnouncement(Long id, AnnouncementRequestDTO dto) {
        Announcement a = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found with id: " + id));

        a.setDateTime(LocalDateTime.now());
        a.setMessage(dto.getMessage());

        List<String> roles = dto.getTargetRoles();
        if (roles != null && roles.contains("ALL")) {
            a.setTargetRoles(String.join(",", ALL_ROLES));
        } else if (roles != null && !roles.isEmpty()) {
            a.setTargetRoles(String.join(",", roles));
        } else {
            a.setTargetRoles("");
        }

        Announcement updated = announcementRepository.save(a);
        return convertToResponseDTO(updated);
    }

    // Delete an announcement
    public void deleteAnnouncement(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new RuntimeException("Announcement not found with id: " + id);
        }
        announcementRepository.deleteById(id);
    }

    // Convert entity to DTO (with proper role handling)
    private AnnouncementResponseDTO convertToResponseDTO(Announcement a) {
        String rolesStr = a.getTargetRoles();
        List<String> roles;

        if (rolesStr == null || rolesStr.isBlank()) {
            roles = Collections.emptyList();
        } else {
            roles = Arrays.stream(rolesStr.split(","))
                    .map(String::trim)
                    .filter(r -> !r.isEmpty())
                    .collect(Collectors.toList());

            // If all roles are present, collapse to "ALL"
            if (roles.containsAll(ALL_ROLES) && roles.size() == ALL_ROLES.size()) {
                roles = List.of("ALL");
            }
        }

        return new AnnouncementResponseDTO(
                a.getAnnouncementID(),
                a.getMessage(),
                a.getDateTime(),
                roles
        );
    }

    // Get announcements targeted to a specific role
    public List<Announcement> getAnnouncementsForRole(String role) {
        return announcementRepository.findAll().stream()
                .filter(a -> {
                    String rolesStr = a.getTargetRoles();
                    if (rolesStr == null || rolesStr.isBlank()) {
                        return false;
                    }
                    List<String> roles = Arrays.stream(rolesStr.split(","))
                            .map(String::trim)
                            .toList();
                    return roles.contains(role);
                })
                .collect(Collectors.toList());
    }
}
