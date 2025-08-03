package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "bottle_type")
public class Bottletype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bottleid;
    private String name;
    private String volume;

    public Bottletype(){

    }

}
