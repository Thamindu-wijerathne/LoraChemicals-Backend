package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "CustomerOrder")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Long orderid;

    private Date delivered_date;

    private String status;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(
            name = "customerid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_user_order", foreignKeyDefinition = "FOREIGN KEY (customerid) REFERENCES users(id) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private User user;
}
