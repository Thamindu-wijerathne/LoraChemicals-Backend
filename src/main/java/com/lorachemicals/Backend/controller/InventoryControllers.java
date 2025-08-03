package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.InventoryRequestDTO;
import com.lorachemicals.Backend.dto.InventoryResponseDTO;
import com.lorachemicals.Backend.services.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryControllers {
    @Autowired
    private InventoryService invservice;

    @GetMapping("/all")
    public ResponseEntity<?> getall(HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
           List<InventoryResponseDTO> inventory =  invservice.getall();
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id , HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            invservice.deleteInventory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getall(@PathVariable Long id,HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            InventoryResponseDTO inventory =  invservice.getbyid(id);

            return ResponseEntity.ok(inventory);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody InventoryRequestDTO inwreq, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            InventoryResponseDTO response = invservice.createInventory(inwreq);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> Update(@PathVariable Long id,@RequestBody InventoryRequestDTO inwreq, HttpServletRequest request){
        AccessControlUtil.checkAccess(request, "admin");
        try{
            InventoryResponseDTO response = invservice.updateinv(id,inwreq);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
