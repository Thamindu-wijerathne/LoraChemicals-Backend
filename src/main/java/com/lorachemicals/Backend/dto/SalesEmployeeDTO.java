package com.lorachemicals.Backend.dto;

public class SalesEmployeeDTO {
    private String employeeName;
    private Double totalSales;
    private Long totalOrders;

    public SalesEmployeeDTO(String employeeName, Double totalSales, Long totalOrders) {
        this.employeeName = employeeName;
        this.totalSales = totalSales;
        this.totalOrders = totalOrders;
    }

    // Getters and Setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }
}
