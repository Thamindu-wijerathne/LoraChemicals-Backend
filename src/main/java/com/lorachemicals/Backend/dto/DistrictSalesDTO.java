package com.lorachemicals.Backend.dto;

import java.math.BigDecimal;

public class DistrictSalesDTO {
    private String district;
    private BigDecimal totalSales;

    public DistrictSalesDTO(String district, BigDecimal totalSales) {
        this.district = district;
        this.totalSales = totalSales;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}
