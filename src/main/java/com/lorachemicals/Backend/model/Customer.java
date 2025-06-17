package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerid;

    @OneToOne
    @JoinColumn(
            name = "userid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_customer_user", foreignKeyDefinition = "FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;

    private String shop_name;

    @OneToOne
    @JoinColumn(
            name = "srepid",
            referencedColumnName = "srepid",
            foreignKey = @ForeignKey(name = "fk_customer_salesrep", foreignKeyDefinition = "FOREIGN KEY (srepid) REFERENCES sales_rep(srepid) ON DELETE RESTRICT ON UPDATE SET NULL")
    )
    private SalesRep salesRep;

    @OneToOne
    @JoinColumn(
            name = "routeid",
            referencedColumnName = "routeid",
            foreignKey = @ForeignKey(name = "fk_customer_route", foreignKeyDefinition = "FOREIGN KEY (routeid) REFERENCES route(routeid) ON DELETE RESTRICT ON UPDATE SET NULL")
    )
    private Route route;

    public Customer() {}

    public Long getCustomerid() {return customerid;}

    public void setCustomerid(Long customerid) {this.customerid = customerid;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public String getShop_name() {return shop_name;}

    public void setShop_name(String shop_name) {this.shop_name = shop_name;}

    public SalesRep getSalesRep() {return salesRep;}

    public void setSalesRep(SalesRep salesRep) {this.salesRep = salesRep;}

    public Route getRoute() {return route;}

    public void setRoute(Route route) {this.route = route;}
}