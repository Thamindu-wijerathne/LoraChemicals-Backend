package com.lorachemicals.Backend.dto;

public class RawMaterialTypeResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String category;
    private String status;

    // Constructors
    public RawMaterialTypeResponseDTO() {}

    public RawMaterialTypeResponseDTO(Long id, String name, String description, String category, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
