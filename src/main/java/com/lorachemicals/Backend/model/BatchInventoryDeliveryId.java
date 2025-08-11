package com.lorachemicals.Backend.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BatchInventoryDeliveryId implements Serializable {

    private Long batchtypeid;
    private Long deliveryid;
    private LocalDateTime datetime;
    private String type;


    public BatchInventoryDeliveryId() {}

    public BatchInventoryDeliveryId(Long batchtypeid, Long deliveryid, LocalDateTime datetime, String type) {
        this.batchtypeid = batchtypeid;
        this.deliveryid = deliveryid;
        this.datetime = datetime;
        this.type = type;
    }

    // Explicitly override equals and hashCode for embedded IDs
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchInventoryDeliveryId)) return false;
        BatchInventoryDeliveryId that = (BatchInventoryDeliveryId) o;
        return Objects.equals(batchtypeid, that.batchtypeid) &&
                Objects.equals(deliveryid, that.deliveryid) &&
                Objects.equals(datetime, that.datetime) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchtypeid, deliveryid, datetime, type);
    }
}
