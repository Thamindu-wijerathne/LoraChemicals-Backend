package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private final Long id;
    private final String fname;
    private final String lname;
    private final String email;
    private final String role;
    private final String address;
    private final String phone;
    private final String nic;
    private String password;
    private String status;

    // Constructor
    public UserResponseDTO(Long id, String fname, String lname, String email, String role, String address, String phone,String nic, String status) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.nic = nic;
        this.status = status;
    }

    // Getters
    public Long getId() { return id; }

    public String getFname() { return fname; }

    public String getLname() { return lname; }

    public String getFullName() { return fname + " " + lname; }

    public String getEmail() { return email; }

    public String getRole() { return role; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public String getNic() { return nic; }

    public String getPassword() { return password; }

    public String getStatus() { return status; }

}
