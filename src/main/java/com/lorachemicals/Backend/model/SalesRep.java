package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@Entity
@Table(name = "sales_rep")
public class SalesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srepid;

    @OneToOne
    @JoinColumn(
            name = "userid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_salesrep_user",
                    foreignKeyDefinition = "FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"
            )
    )
    private User user;

    // Constructor
    public SalesRep() {}

    public Long getSrepid() { return this.srepid; }
    public void setUser(User user) {
        this.user = user;
    }


}
