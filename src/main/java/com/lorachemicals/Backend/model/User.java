// Purpose: Defines the structure of the User table in the database.
package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    // Getters
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

    private String status;

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



    @Override
    public String toString() {
        return "User{id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getName() {
        return fname + " " + lname;
    }

    public void setPassword(String hashedPassword) {
    }

    public void setLname(String lname) {
    }
}
