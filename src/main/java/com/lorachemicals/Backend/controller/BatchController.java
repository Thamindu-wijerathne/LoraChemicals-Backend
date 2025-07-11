package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BatchRequestDTO;
import com.lorachemicals.Backend.dto.BatchResponseDTO;
import com.lorachemicals.Backend.model.Batch;
import com.lorachemicals.Backend.services.BatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    BatchService batchService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllBatches(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try{
            List<Batch> batches = batchService.getAllBatches();
            return new ResponseEntity<>(batches, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBatchById(HttpServletRequest request, @PathVariable long id) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            Batch batch = batchService.getBatchById(id);
            return new ResponseEntity<>(batch, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBatch(@RequestBody BatchRequestDTO batchRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            Batch createdBatch = batchService.createBatch(batchRequestDTO);
            return new ResponseEntity<>(createdBatch, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/{prodid}")
    public ResponseEntity<?> addBatchByProduction(@PathVariable Long prodid, @RequestBody BatchRequestDTO batchRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try{
            Batch batch = batchService.createByProdid(prodid, batchRequestDTO);
            return new ResponseEntity<>(batch, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
