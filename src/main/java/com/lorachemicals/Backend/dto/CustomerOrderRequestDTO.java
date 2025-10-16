package com.lorachemicals.Backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CustomerOrderRequestDTO {
    private Date deliveredDate;
    private String status;
    private BigDecimal total;
    private Long customerId;
    private String feedback;
    private BigDecimal rate;
    private List<CustomerOrderItemRequestDTO> orderItems;

    // Directly store batch deductions as a list of maps or a simple inner class
    private List<BatchDeduction> batchDeductions;

    @Getter
    @Setter
    public static class BatchDeduction {
        private Long batchtypeid;
        private Long deliveryid;
        private String type; // "orders" or "extras"
        private Integer boxesToDeduct;
        private LocalDateTime datetime; // add this if using datetime



        @Override
        public String toString() {
            return "BatchDeduction{" +
                    "batchtypeid=" + batchtypeid +
                    ", deliveryid=" + deliveryid +
                    ", type='" + type + '\'' +
                    ", boxesToDeduct=" + boxesToDeduct +
                    ", datetime" + datetime +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "CustomerOrderRequestDTO{" +
                "deliveredDate=" + deliveredDate +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", customerId=" + customerId +
                ", orderItems=" + orderItems +
                ", batchDeductions=" + batchDeductions +
                ", feedback" + feedback +
                ", rate" + rate +
                '}';
    }
}
