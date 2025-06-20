package com.lorachemicals.Backend.dto;

public class WarehouseManagerDTO {
    private final Long wmid;
    private final Long userid;

    public WarehouseManagerDTO(Long wmid, Long userid) {
        this.wmid = wmid;
        this.userid = userid;
    }

    public Long getWmid() { return wmid; }
    public Long getUserId() { return userid; }
}
