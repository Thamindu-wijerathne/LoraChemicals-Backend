package com.lorachemicals.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryid;
    private String location;

    public Inventory(){

    }

    public void setID(Long inventoryid){
        this.inventoryid = inventoryid;
    }
    public Long getId(){
        return this.inventoryid;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getLocation(){
        return this.location;
    }




}


//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "inventory")
//public class Inventory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long inventoryid;
//    private String location;
//    // No need to manually write getters and setters
//}
