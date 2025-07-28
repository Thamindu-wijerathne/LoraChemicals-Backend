package com.lorachemicals.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "batch_type_without_box")
public class BatchTypeWithoutBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchtypewithoutboxid;

    @ManyToOne
    @JoinColumn(name = "ptvid", nullable = false)
    private ProductTypeVolume productTypeVolume;

    private String batchtypename;

    public BatchTypeWithoutBox() {
    }
}
