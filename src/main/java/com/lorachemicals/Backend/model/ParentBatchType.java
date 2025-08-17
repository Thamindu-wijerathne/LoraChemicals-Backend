package com.lorachemicals.Backend.model;

import java.time.LocalDateTime;

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
        // Generate unique batch code: BATCH + 5 random numbers
        int randomNumber = (int) (Math.random() * 100000); // 0 to 99999
        this.uniqueBatchCode = "BATCHTYPE-" + String.format("%05d", randomNumber);
    }

    public ParentBatchType() {
    }
}
