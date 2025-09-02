package com.lorachemicals.Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "\"Status\"")
    private Integer status;

    // Constructor
    public SalesRep() {
        this.status = 1; // Initial status is 1 when created by admin
    }

    public SalesRep(User user) {
        this.user = user;
        this.status = 1; // Initial status is 1 when created by admin
    }

    public Long getSrepid() { return this.srepid; }
    public void setUser(User user) {
        this.user = user;
    }


}
