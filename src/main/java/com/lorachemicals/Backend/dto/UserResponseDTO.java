package com.lorachemicals.Backend.dto;

public class UserResponseDTO {
    private final Long id;
    private final String fname;
    private final String lname;
    private final String email;
    private final String role;
    private String password;

    // Constructor
    public UserResponseDTO(Long id, String fname, String lname, String email, String role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }

    public String getFname() { return fname; }

    public String getLname() { return lname; }

    public String getFullName() { return fname + " " + lname; }

    public String getEmail() { return email; }

    public String getRole() { return role; }

    public String getPassword() { return password; }

}
