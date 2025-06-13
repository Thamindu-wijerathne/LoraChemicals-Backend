package com.lorachemicals.Backend.dto;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    // Constructor
    public UserResponseDTO(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
