package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.DamageReportRequestDTO;
import com.lorachemicals.Backend.dto.DamageReportResponseDTO;
import com.lorachemicals.Backend.dto.UserResponseDTO;
import com.lorachemicals.Backend.model.DamageReport;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.DamageReportRepository;
import com.lorachemicals.Backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DamageReportService {
    private final DamageReportRepository repository;
    private final UserRepository userRepository;

    public DamageReportService(DamageReportRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public DamageReportResponseDTO createReport(DamageReportRequestDTO dto) {
        DamageReport report = new DamageReport();
        report.setDamageDate(dto.getDamageDate());
        report.setDamageItem(dto.getDamageItem());
        report.setDescription(dto.getDescription());
        report.setReportDate(dto.getReportDate());
        report.setSourceType(dto.getSourceType());
        report.setStatus(dto.getStatus());

        // Fetch the user from DB
        User user = userRepository.findById(dto.getReportedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setReportedUser(user);

        DamageReport saved = repository.save(report);
        return new DamageReportResponseDTO(saved);
    }

    public List<DamageReportResponseDTO> getAllReports() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper methods
    private String saveImage(MultipartFile file) {
        // Implement file saving logic, return image URL/path
        // For example, save to local disk or cloud storage
        return "/uploads/" + file.getOriginalFilename();
    }

    private DamageReportResponseDTO toResponseDTO(DamageReport report) {
        DamageReportResponseDTO dto = new DamageReportResponseDTO();
        dto.setId(report.getId());
        dto.setDamageDate(report.getDamageDate());
        dto.setDamageItem(report.getDamageItem());
        dto.setDescription(report.getDescription());
        dto.setImageUrl(report.getImageUrl());
        dto.setReportDate(report.getReportDate());
        dto.setSourceType(report.getSourceType());
        dto.setStatus(report.getStatus());

        // Map User -> UserResponseDTO
        if (report.getReportedUser() != null) {
            User u = report.getReportedUser();
            UserResponseDTO userDto = new UserResponseDTO(
                    u.getId(),
                    u.getFname(),
                    u.getLname(),
                    u.getEmail(),
                    u.getRole(),
                    u.getAddress(),
                    u.getPhone(),
                    u.getNic(),
                    u.getStatus()
            );
            dto.setReportedUser(userDto); // now compatible type
        }

        return dto;
    }


    public DamageReportResponseDTO reviewReport(Long id, String action) {
        DamageReport report = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (!report.getStatus().equalsIgnoreCase("pending")) {
            throw new RuntimeException("Report already reviewed");
        }

        if (action.equalsIgnoreCase("approve")) {
            report.setStatus("approved");
        } else if (action.equalsIgnoreCase("reject")) {
            report.setStatus("rejected");
        } else {
            throw new RuntimeException("Invalid action");
        }

        DamageReport updated = repository.save(report);
        return new DamageReportResponseDTO(updated);
    }
}
