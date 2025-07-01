package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.DamageReportRequestDTO;
import com.lorachemicals.Backend.dto.DamageReportResponseDTO;
import com.lorachemicals.Backend.model.DamageReport;
import com.lorachemicals.Backend.repository.DamageReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DamageReportService {
    private final DamageReportRepository repository;

    public DamageReportService(DamageReportRepository repository) {
        this.repository = repository;
    }

    public DamageReportResponseDTO createReport(DamageReportRequestDTO dto) {
        DamageReport report = new DamageReport();
        report.setDamageDate(dto.getDamageDate());
        report.setDamageItem(dto.getDamageItem());
        report.setDescription(dto.getDescription());
        report.setReportDate(dto.getReportDate());
        report.setReportedUser(dto.getReportedUser());
        report.setSourceType(dto.getSourceType());

        // Handle image upload (save file and set URL)
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = saveImage(dto.getImage());
            report.setImageUrl(imageUrl);
        }

        DamageReport saved = repository.save(report);
        return toResponseDTO(saved);
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
        dto.setReportedUser(report.getReportedUser());
        dto.setSourceType(report.getSourceType());
        return dto;
    }
}
