package com.lorachemicals.Backend.dto;

public class SalesRepDTO {
    private final Long srepid;
    private final Long userid;
    private final int status;

    public SalesRepDTO(Long srepid, Long userid, int status) {
        this.srepid = srepid;
        this.userid = userid;
        this.status = status;
    }

    public Long getSrepid() { return srepid; }
    public Long getUserId() { return userid; }
    public int getStatus() { return status; }
}
