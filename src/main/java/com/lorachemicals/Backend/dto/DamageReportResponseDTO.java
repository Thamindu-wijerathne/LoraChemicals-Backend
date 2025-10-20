package com.lorachemicals.Backend.dto;

import com.lorachemicals.Backend.model.DamageReport;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DamageReportResponseDTO {
    private Long id;
    private LocalDate damageDate;
    private String damageItem;
    private String description;
    private String imageUrl;
    private LocalDate reportDate;
    private UserResponseDTO reportedUser; // Use full DTO now
    private String sourceType;
    private String status;

    // ✅ Constructor mapping from entity
    public DamageReportResponseDTO(DamageReport report) {
        this.id = report.getId();
        this.damageItem = report.getDamageItem();
        this.description = report.getDescription();
        this.sourceType = report.getSourceType();
        this.reportDate = report.getReportDate();
        this.damageDate = report.getDamageDate();
        this.status = report.getStatus();
        this.imageUrl = report.getImageUrl();
    }

    // ✅ Empty constructor
    public DamageReportResponseDTO() {}

}
