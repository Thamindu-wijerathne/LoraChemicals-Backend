package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.model.Delivery;
import com.lorachemicals.Backend.services.DeliveryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try{
            List<Delivery> delivery = deliveryService.getAllDeliveries();
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try{
            Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
            if(delivery.isPresent()){
                return new ResponseEntity<>(delivery.get(), HttpStatus.OK);
            } else{
                return new ResponseEntity<>("Delivery not found",HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        try{
            deliveryService.deleteDeliveryById(id);
            return new ResponseEntity<>("Delivery is successfully deleted!",HttpStatus.OK);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}
