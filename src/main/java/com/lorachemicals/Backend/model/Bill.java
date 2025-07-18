package com.lorachemicals.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billid;

    private Long total;

    private Date datetime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "srepid", nullable = false, foreignKey = @ForeignKey(name = "fk_bill_salesrep"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SalesRep salesRep;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> billItems = new ArrayList<>();

    @Override
    public String toString() {
        return "Bill{" +
                "billid=" + billid +
                ", total=" + total +
                ", datetime=" + datetime +
                ", salesRepId=" + (salesRep != null ? salesRep.getSrepid() : "null") +
                '}';
    }
}
