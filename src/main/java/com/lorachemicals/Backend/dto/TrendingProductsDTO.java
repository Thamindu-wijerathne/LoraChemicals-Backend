package com.lorachemicals.Backend.dto;

import java.math.BigDecimal;

public class TrendingProductsDTO {
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private Long totalOrdered;

    public TrendingProductsDTO(String name, BigDecimal price, String imageUrl, Long totalOrdered) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.totalOrdered = totalOrdered;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getTotalOrdered() {
        return totalOrdered;
    }

    public void setTotalOrdered(Long totalOrdered) {
        this.totalOrdered = totalOrdered;
    }
}
