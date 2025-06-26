package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "label_type")
public class Labeltype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labelId;

    @Column(nullable = false)
    private String name;

    private String volume;

    public Long getLabelid() {
        return labelId;
    }
}
