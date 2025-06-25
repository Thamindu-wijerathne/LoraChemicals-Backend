package com.lorachemicals.Backend.dto;

public class ProductTypeResponseDto {
    private Long id;
    private String name;
    private String details;

    public ProductTypeResponseDto(Long id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }
}
