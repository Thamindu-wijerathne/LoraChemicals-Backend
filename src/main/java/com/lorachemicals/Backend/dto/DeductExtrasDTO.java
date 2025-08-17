package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;

public class DeductExtrasDTO {
    private Long batchtypeid;
    private Long deliveryid;
    private String type;
    private LocalDateTime datetime;
    private int boxesToDeduct;

    // Default constructor
    public DeductExtrasDTO() {}

    // Constructor with parameters
    public DeductExtrasDTO(Long batchtypeid, Long deliveryid, String type, LocalDateTime datetime, int boxesToDeduct) {
        this.batchtypeid = batchtypeid;
        this.deliveryid = deliveryid;
        this.type = type;
        this.datetime = datetime;
        this.boxesToDeduct = boxesToDeduct;
    }

    // Getters and Setters
    public Long getBatchtypeid() {
        return batchtypeid;
    }

    public void setBatchtypeid(Long batchtypeid) {
        this.batchtypeid = batchtypeid;
    }

    public Long getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(Long deliveryid) {
        this.deliveryid = deliveryid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getBoxesToDeduct() {
        return boxesToDeduct;
    }

    public void setBoxesToDeduct(int boxesToDeduct) {
        this.boxesToDeduct = boxesToDeduct;
    }

    @Override
    public String toString() {
        return "DeductExtrasDTO{" +
                "batchtypeid=" + batchtypeid +
                ", deliveryid=" + deliveryid +
                ", type='" + type + '\'' +
                ", datetime=" + datetime +
                ", boxesToDeduct=" + boxesToDeduct +
                '}';
    }
}
