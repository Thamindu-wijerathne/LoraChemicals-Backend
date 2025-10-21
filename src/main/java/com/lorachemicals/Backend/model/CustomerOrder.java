package com.lorachemicals.Backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "customer_order")  // changed to lowercase snake_case
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Long orderid;

    @Column(name = "ordercode", unique = true)
    private String ordercode;

    private Date delivered_date;

    private String status;

    private BigDecimal total;

    private String feedback;

    private BigDecimal rate;

    private Date orderedDate;


    @ManyToOne
    @JoinColumn(
            name = "customerid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_user_order",
                    foreignKeyDefinition = "FOREIGN KEY (customerid) REFERENCES users(id) ON DELETE SET NULL ON UPDATE SET NULL"
            )
    )
    private User user;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerOrderItem> orderItems;

    // Generate unique order code: ORD + YYYYMMDD + random number
    public static String generateOrderCode() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = currentDate.format(formatter);
        
        // Generate random 4-digit number
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000); // generates 4-digit number (1000-9999)
        
        return "ORD" + dateString + randomNum;
    }
}
