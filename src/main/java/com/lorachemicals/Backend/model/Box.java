package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "box",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "boxid")
        }
)
@PrimaryKeyJoinColumn(name = "inventoryid")
@Getter
@Setter
public class Box extends RawMaterial {

    @ManyToOne
    @JoinColumn(name = "boxid", nullable = false)
    private BoxType boxType;

    private int quantity;

    public void setId(Long id) {
        this.setInventoryid(id);
    }
}
