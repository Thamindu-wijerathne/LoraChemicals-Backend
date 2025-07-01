package com.lorachemicals.Backend.dto;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

public class DamageReportRequestDTO {
    private LocalDate damageDate;
    private String damageItem;
    private String description;
    private MultipartFile image; // For file uploads
    private LocalDate reportDate;
    private String reportedUser;
    private String sourceType;

    // Getters and Setters

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    @Override
    public String toString() {
        return "DamageReportResponseDTO{" +
                ", damageDate=" + damageDate +
                ", damageItem='" + damageItem + '\'' +
                ", description='" + description + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
                ", reportDate=" + reportDate +
                ", reportedUser='" + reportedUser + '\'' +
                ", sourceType='" + sourceType + '\'' +
                '}';
    }
}
