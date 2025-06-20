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
            foreignKey = @ForeignKey(name = "fk_customer_user", foreignKeyDefinition = "FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;

    private String shopName;

    @ManyToOne
    @JoinColumn(
            name = "srepid",
            referencedColumnName = "srepid",
            foreignKey = @ForeignKey(name = "fk_customer_salesrep", foreignKeyDefinition = "FOREIGN KEY (srepid) REFERENCES sales_rep(srepid) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private SalesRep salesRep;


    @ManyToOne
    @JoinColumn(
            name = "routeid",
            referencedColumnName = "routeid",
            foreignKey = @ForeignKey(name = "fk_customer_route", foreignKeyDefinition = "FOREIGN KEY (routeid) REFERENCES route(routeid) ON DELETE SET NULL ON UPDATE SET NULL")
    )
    private Route route;

    public Customer() {}

    public Long getCustomerid() {return customerid;}

    public void setCustomerid(Long customerid) {this.customerid = customerid;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public String getShopName() {return shopName;}

    public void setShopName(String shopName) {this.shopName = shopName;}

    public SalesRep getSalesRep() {return salesRep;}

    public void setSalesRep(SalesRep salesRep) {this.salesRep = salesRep;}

    public Route getRoute() {return route;}

    public void setRoute(Route route) {this.route = route;}

    @Override
    public String toString() {
        return "Customer{" +
                "customerid=" + customerid +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", shop_name='" + shopName + '\'' +
                ", salesRepId=" + (salesRep != null ? salesRep.getSrepid() : "null") +
                ", routeId=" + (route != null ? route.getRouteid() : "null") +
                '}';
    }


}