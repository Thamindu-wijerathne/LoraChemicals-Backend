package com.lorachemicals.Backend.dto;

public class CustomerDTO {
    private Long customerid;
    private Long userid;
    private String shop_name;
    private Long srepid;
    private Long routeid;

    public CustomerDTO(Long customerid, Long userid, String shop_name, Long srepid, Long routeid) {
        this.customerid = customerid;
        this.userid = userid;
        this.shop_name = shop_name;
        this.srepid = srepid;
        this.routeid = routeid;
    }

    public Long getCustomerid() { return customerid; }
    public Long getUserId() { return userid; }
    public String getShopName() { return shop_name; }
    public Long getSrepid() { return srepid; }
    public Long getRouteid() { return routeid; }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerid=" + customerid +
                ", userid=" + userid +
                ", shop_name='" + shop_name + '\'' +
                ", srepid=" + srepid +
                ", routeid=" + routeid +
                '}';
    }
}
