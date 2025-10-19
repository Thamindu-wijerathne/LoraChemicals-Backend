package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Getter
@Setter
public class DamageReportRequestDTO {
    private LocalDate damageDate;
    private String damageItem;
    private String description;
    private MultipartFile image; // For file uploads
    private LocalDate reportDate;
    private String reportedUser;
    private String sourceType;
    private String status;

    private Long reportedUserId; // add this

    @Override
    public String toString() {
        return "DamageReportResponseDTO{" +
                ", damageDate=" + damageDate +
                ", damageItem='" + damageItem + '\'' +
                ", description='" + description + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
                ", reportDate=" + reportDate +
                ", status=" + status +
                ", reportedUser='" + reportedUser + '\'' +
                ", sourceType='" + sourceType + '\'' +
                '}';
    }

}
