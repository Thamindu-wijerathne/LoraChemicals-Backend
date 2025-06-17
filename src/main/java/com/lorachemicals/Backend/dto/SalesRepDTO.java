package com.lorachemicals.Backend.dto;

public class SalesRepDTO {
    private final Long srepid;
    private final Long userid;

    public SalesRepDTO(Long srepid, Long userid) {
        this.srepid = srepid;
        this.userid = userid;
    }

    public Long getSrepid() { return srepid; }
    public Long getUserId() { return userid; }
}
