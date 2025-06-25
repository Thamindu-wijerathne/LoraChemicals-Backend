package com.lorachemicals.Backend.dto;

public class RawMaterialTypeRequestDTO {

    private String name;
    private String description;
    private String category;
    private String status;

    // Constructors
    public RawMaterialTypeRequestDTO() {}

    public RawMaterialTypeRequestDTO(String name, String description, String category, String status) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Overridden toString() method
    @Override
    public String toString() {
        return "RawMaterialTypeRequestDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
