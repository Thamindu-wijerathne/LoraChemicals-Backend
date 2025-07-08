package com.lorachemicals.Backend.dto;

public class UserRequestDTO {
    public String fname;
    public String lname;
    public String nic;
    public String phone;
    public String address;
    public String email;
    public String password;
    public String role;
    public String status;

    // Customer-specific fields
    public String shop_name;
    public Long srepid;
    public Long routeid;

    // No-args constructor (required for deserialization)
    public UserRequestDTO() {}

    // Optionally, add getters and setters (recommended for cleaner access)
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Long getSrepid() {
        return srepid;
    }

    public void setSrepid(Long srepid) {
        this.srepid = srepid;
    }

    public Long getRouteid() {
        return routeid;
    }

    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
