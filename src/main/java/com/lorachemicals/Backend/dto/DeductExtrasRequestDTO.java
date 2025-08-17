package com.lorachemicals.Backend.dto;

import java.util.List;

public class DeductExtrasRequestDTO {
    private Long srepid;
    private List<DeductExtrasDTO> deductions;

    // Default constructor
    public DeductExtrasRequestDTO() {}

    // Constructor with parameters
    public DeductExtrasRequestDTO(Long srepid, List<DeductExtrasDTO> deductions) {
        this.srepid = srepid;
        this.deductions = deductions;
    }

    // Getters and Setters
    public Long getSrepid() {
        return srepid;
    }

    public void setSrepid(Long srepid) {
        this.srepid = srepid;
    }

    public List<DeductExtrasDTO> getDeductions() {
        return deductions;
    }

    public void setDeductions(List<DeductExtrasDTO> deductions) {
        this.deductions = deductions;
    }

    @Override
    public String toString() {
        return "DeductExtrasRequestDTO{" +
                "srepid=" + srepid +
                ", deductions=" + deductions +
                '}';
    }
}
