package com.lorachemicals.Backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesRepDeliveryResponseDTO {
    private Long deliveryid;
    private LocalDateTime dispatchdate;
    private LocalDateTime camedate;
    private Integer status;
    private Long srepid;
    private String salesRepName;
    private String salesRepEmail;
    private Long routeid;
    private String routeName;
    private Long vehicleid;
    private String vehicleNumber;
    private String vehicleLicensePlate;
    
    // Delivery batch inventory details
    private List<DeliveryBatchInventoryDetail> batchInventoryDetails;
    
    // Delivery orders
    private List<DeliveryOrderDetail> deliveryOrders;

    @Getter
    @Setter
    public static class DeliveryBatchInventoryDetail {
        private Long batchtypeid;
        private String batchTypeName;
        private Long deliveryid;
        private LocalDateTime datetime;
        private String type; // "orders" or "extras"
        private Integer quantity;
        private Integer currentQuantity;
        private Long wmid;
        private String warehouseManagerName;
        
        // Additional batch details
        private String inventoryType; // "with_box" or "without_box"
        private String productName;
        private Double volume;
        private String boxTypeName;
        private Integer quantityInBox;
        
        // Parent batch information
        private Long parentBatchId;
        private String batchCode;
        private String parentBatchCode;
        private int ptvid;
        private String imageurl;
    }

    @Getter
    @Setter
    public static class DeliveryOrderDetail {
        private Long orderid;
        private Long deliveryid;
        private String orderStatus;
        private Double orderTotal;
        private Long customerId;
        private String customerName;
        private String customerEmail;
        private String customerAddress;
        private String customerPhone;
        private LocalDateTime orderDate;
        private String deliveredDate;
        
        // Order items
        private List<OrderItem> orderItems;
    }

    @Getter
    @Setter
    public static class OrderItem {
        private Long itemid;
        private String productTypeName;
        private Double volume;
        private Integer quantity;
        private Double unitPrice;
        private Double totalPrice;
        private String productImage;
    }
}
