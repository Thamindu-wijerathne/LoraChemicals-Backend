// Purpose: Defines the structure of the User table in the database.
package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fname;

    private String lname;

    private String nic;

    private String phone;

    private String address;

    private String email;

    private String password;

    private String role;

    // Default constructor
    public User() {}

    // Constructor with fields
    public User(String fname, String lname, String email, String password, String role) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }

    public String getFname() { return fname; }

    public String getLname() { return lname; }

    public String getNic() { return nic; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    // Setters
    public void setId(Long id) { this.id = id; }

    public void setFname(String fname) { this.fname = fname; }

    public void setLname(String lname) { this.lname = lname; }

    public void setNic(String nic) { this.nic = nic; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setAddress(String address) { this.address = address; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
