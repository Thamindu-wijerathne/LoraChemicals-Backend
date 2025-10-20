package com.lorachemicals.Backend.dto;

import java.math.BigDecimal;

// DTO to represent orders grouped by month
public class MonthlyOrderDTO {

    private Integer month;       // Month number (1 = January, 12 = December)
    private Long totalOrders;    // Total number of orders in that month
    private BigDecimal totalAmount; // Sum of order totals for that month

    public MonthlyOrderDTO(Integer month, Long totalOrders, BigDecimal totalAmount) {
        this.month = month;
        this.totalOrders = totalOrders;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
