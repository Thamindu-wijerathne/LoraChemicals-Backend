package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouse_manager")
public class WarehouseManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wmid;

    @OneToOne
    @JoinColumn(
            name = "userid",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_wm_user", foreignKeyDefinition = "FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;

    //constructor
    public WarehouseManager() {}

    public Long getWmid() {return wmid;}

    public void setWmid(Long wmid) {this.wmid = wmid;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}
}
