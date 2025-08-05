package com.lorachemicals.Backend.dto;

public class SupplierDTO {

    private Long supplierid;
    private String firstName;
    private String lastName;
    private String brId;
    private String phone;
    private String address;
    private String email;
    private String supplierType; // e.g., Chemical, Box, Label, Bottle

    // Constructors
    public SupplierDTO() {}

    public SupplierDTO(Long supplierid, String firstName, String lastName, String brId,
                       String phone, String address, String email,
                       String supplierType, String status) {
        this.supplierid = supplierid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.brId = brId;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.supplierType = supplierType;
    }

    // Getters and Setters
    public Long getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Long supplierid) {
        this.supplierid = supplierid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBrId() {
        return brId;
    }

    public void setBrId(String brId) {
        this.brId = brId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

}
