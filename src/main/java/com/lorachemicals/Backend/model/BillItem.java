package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bill_item")
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billitemid;

    private Long total;

    private Long quantity;

    @ManyToOne
    @JoinColumn(
            name = "ptvid",
            referencedColumnName = "ptvid",
            foreignKey = @ForeignKey(
                    name = "fk_billitem_ptvid",
                    foreignKeyDefinition = "FOREIGN KEY (ptvid) REFERENCES product_type_volume(ptvid) ON DELETE SET NULL ON UPDATE SET NULL"
            )
    )
    private ProductTypeVolume productTypeVolume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cbillid", // Use cbillid as the foreign key column name
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_billitem_bill")
    )
    private Bill bill;
}
