package com.lorachemicals.Backend.dto;

public class RawmaterialRequestDTO {
    private Long rmtid;
    private Long inventoryid;

    public RawmaterialRequestDTO(){}

    public void setRmtid(Long rmtid){
        this.rmtid = rmtid;
    }
    public Long getRmtid(){
        return this.rmtid;
    }

    public void setInventoryid(Long inventoryid){
        this.inventoryid = inventoryid;
    }
    public Long getInventoryid(){
        return this.inventoryid;
    }

    @Override
    public String toString() {
        return "RawmaterialRequestDTO{" + "rmtid=" + rmtid + ", inventoryid=" + inventoryid + '}';
    }
}
