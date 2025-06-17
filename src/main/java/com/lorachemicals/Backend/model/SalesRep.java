package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sales_rep")
public class SalesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long srepid;

    @ManyToOne
    @JoinColumn(
            name = "userid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_salesrep_user", foreignKeyDefinition = "FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;

    //Constructor
    public SalesRep() {}

    public Long getSrepid() {return srepid;}

    public void setSrepid(Long srepid) {this.srepid = srepid;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}
}
