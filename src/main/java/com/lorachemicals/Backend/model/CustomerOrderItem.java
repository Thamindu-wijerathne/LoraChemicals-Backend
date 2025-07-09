package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "customer_order_item")  // changed to lowercase snake_case
public class CustomerOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemid;

    private Long quantity;

    private BigDecimal productTotal;

    @ManyToOne
    @JoinColumn(
            name = "orderid",
            referencedColumnName = "orderid",
            foreignKey = @ForeignKey(
                    name = "fk_orderItem_order",
                    foreignKeyDefinition = "FOREIGN KEY (orderid) REFERENCES customer_order(orderid) ON DELETE SET NULL ON UPDATE SET NULL"
            )
    )
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(
            name = "ptvid",
            referencedColumnName = "ptvid",
            foreignKey = @ForeignKey(
                    name = "fk_orderItem_ptvid",
                    foreignKeyDefinition = "FOREIGN KEY (ptvid) REFERENCES product_type_volume(ptvid) ON DELETE SET NULL ON UPDATE SET NULL"
            )
    )
    private ProductTypeVolume productTypeVolume;
}
