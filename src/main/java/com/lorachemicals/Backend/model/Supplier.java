package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierid;

    private String firstName;

    private String lastName;

    private String brId;

    private String phone;

    private String address;

    private String email;

    private String supplierType; // e.g., chemical, box, label, bottle

    private String status; // e.g., active, inactive

    // Default constructor
    public Supplier() {}

    // Optional constructor (you can customize more as needed)
    public Supplier(String firstName, String lastName, String brId, String phone, String address, String email, String supplierType, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.brId = brId;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.supplierType = supplierType;
        this.status = status;
    }

    // Getters
    public Long getSupplierid() {
        return supplierid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBrId() {
        return brId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setSupplierid(Long supplierid) {
        this.supplierid = supplierid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBrId(String brId) {
        this.brId = brId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierid=" + supplierid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", brId='" + brId + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", supplierType='" + supplierType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
