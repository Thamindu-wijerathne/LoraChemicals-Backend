package com.lorachemicals.Backend.dto;

public class CustomerUserDTO {
    private Long customerId;
    private String shop_name;
    private Long srepid;
    private Long routeid;

    private Long userId;
    private String fname;
    private String lname;
    private String email;
    private String role;
    private String address;
    private String phone;
    private String nic;

    // Constructor
    public CustomerUserDTO(Long customerId, String shopName, Long salesRepId, Long routeId,
                           Long userId, String fname, String lname, String email, String role,
                           String address, String phone, String nic) {
        this.customerId = customerId;
        this.shop_name = shopName;
        this.srepid = salesRepId;
        this.routeid = routeId;
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.nic = nic;
    }

    // Getters
    public Long getCustomerId() {
        return customerId;
    }

    public String getShop_name() {
        return shop_name;
    }

    public Long getSrepid() {
        return srepid;
    }

    public Long getRouteid() {
        return routeid;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getNic() {
        return nic;
    }

    @Override
    public String toString() {
        return "CustomerUserDTO{" +
                "customerId=" + customerId +
                ", shopName='" + shop_name + '\'' +
                ", salesRepId=" + srepid +
                ", routeId=" + routeid +
                ", userId=" + userId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
