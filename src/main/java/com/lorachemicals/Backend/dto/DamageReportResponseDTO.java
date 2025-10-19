package com.lorachemicals.Backend.dto;


import com.lorachemicals.Backend.model.DamageReport;

import java.time.LocalDate;

public class DamageReportResponseDTO {
    private Long id;
    private LocalDate damageDate;
    private String damageItem;
    private String description;
    private String imageUrl;
    private LocalDate reportDate;
    private String reportedUser;
    private String sourceType;
    private String status;

    // ✅ Constructor for mapping directly from entity
    public DamageReportResponseDTO(DamageReport report) {
        this.id = report.getId();
        this.damageItem = report.getDamageItem();
        this.description = report.getDescription();
        this.sourceType = report.getSourceType();
        this.reportDate = report.getReportDate();
        this.damageDate = report.getDamageDate();
        this.status = report.getStatus();
        this.reportedUser = report.getReportedUser();
    }

    // ✅ Empty constructor (required for JSON serialization)
    public DamageReportResponseDTO() {}

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    public String getDamageItem() {
        return damageItem;
    }

    public void setDamageItem(String damageItem) {
        this.damageItem = damageItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(String reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DamageReportResponseDTO{" +
                "id=" + id +
                ", damageDate=" + damageDate +
                ", damageItem='" + damageItem + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", reportDate=" + reportDate +
                ", status=" + status +
                ", reportedUser='" + reportedUser + '\'' +
                ", sourceType='" + sourceType + '\'' +
                '}';
    }
}

