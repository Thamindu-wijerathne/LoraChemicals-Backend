package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeid;

    private String district;

    public Route() {}

    public Long getRouteid() {return routeid;}

    public void setRouteid(Long routeid) {this.routeid = routeid;}

    public String getDistrict() {return district;}

    public void setDistrict(String district) {this.district = district;}
}
