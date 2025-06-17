package com.lorachemicals.Backend.dto;

public class RouteDTO {
    private final Long routeid;
    private final String district;

    public RouteDTO(Long routeid, String district) {
        this.routeid = routeid;
        this.district = district;
    }

    public Long getRouteid() { return routeid; }
    public String getDistrict() { return district; }
}
