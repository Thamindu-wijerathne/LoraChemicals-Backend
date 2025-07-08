package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chemical_usage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChemicalUsage {

    @EmbeddedId
    private ChemicalUsageId chemicalUsageId;

    @ManyToOne
    @MapsId("inventoryid")
    @JoinColumn(name = "inventoryid")
    private RawChemical chemical;

    @ManyToOne
    @MapsId("prodid")
    @JoinColumn(name = "prodid")
    private Production production;

    private Double quantity;

}
