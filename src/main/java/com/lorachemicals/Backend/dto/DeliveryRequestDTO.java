package com.lorachemicals.Backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRequestDTO {
    private String dispatchdate;
    private String camedate;
    private int status;
    private Long srepid;
    private Long routeid;
    private Long vehicleid;
    private List<SelectedOrder> selectedOrders;
    private List<BatchInventoryDetail> batchInventoryDetails;

    @Getter
    @Setter
    public static class SelectedOrder {
        private Long orderid;
        private String status;
        private Double total;
        private Long customerId;
        private String deliveredDate;
    }

    @Getter
    @Setter
    public static class BatchInventoryDetail {
        private Long batchtypeid; // The actual BatchType ID for relationships
        private Long inventoryid; // The specific inventory record ID to deduct from
        private String inventoryType; // "with_box" or "without_box"
        private String type; // "orders" or "extras"
        private Long wmid;
        private Integer quantity;
        private Integer currentQuantity;
        private Long parentBatchTypeId; // Include parent batch type ID
    }
}
