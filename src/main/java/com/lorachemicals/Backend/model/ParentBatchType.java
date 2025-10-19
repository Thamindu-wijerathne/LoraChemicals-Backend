package com.lorachemicals.Backend.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parent_batch_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class ParentBatchType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uniqueBatchCode;

    @ManyToOne
    @JoinColumn(name = "ptvid", nullable = false)
    private ProductTypeVolume productTypeVolume;

    private String batchtypename;

    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        if (this.uniqueBatchCode == null) {
            generateUniqueBatchCode();
        }
    }

    private void generateUniqueBatchCode() {
        // Format the current date as yyyyMMdd
        String createdDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Use current time in milliseconds for uniqueness
        long currentTimeMillis = System.currentTimeMillis();

        // Take last 5 digits of time to simulate a random number
        String timeBasedNumber = String.format("%05d", currentTimeMillis % 100000);

        // Create batch code: BATCHTYPE-<date>-<timeBasedNumber>
        this.uniqueBatchCode = "BATCHTYPE-" + createdDate + "-" + timeBasedNumber;
    }

    public ParentBatchType() {
    }
}
