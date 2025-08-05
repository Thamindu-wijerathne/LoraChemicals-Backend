package com.lorachemicals.Backend.dto;

public class AdminDashboardDTO {
    private int totalOrders;

    //create constructor
    public AdminDashboardDTO() {}

    public AdminDashboardDTO(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    // Getter & Setter
    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
