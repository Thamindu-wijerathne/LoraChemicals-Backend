package com.lorachemicals.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.BatchWithoutBoxRequestDTO;
import com.lorachemicals.Backend.model.BatchWithoutBox;
import com.lorachemicals.Backend.services.BatchWithoutBoxService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/batch-without-box")
public class BatchWithoutBoxController {

    @Autowired
    BatchWithoutBoxService batchWithoutBoxService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllBatchesWithoutBox(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try {
            List<BatchWithoutBox> batches = batchWithoutBoxService.getAllBatchesWithoutBox();
            return new ResponseEntity<>(batches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBatchWithoutBoxById(HttpServletRequest request, @PathVariable long id) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            BatchWithoutBox batch = batchWithoutBoxService.getBatchWithoutBoxById(id);
            return new ResponseEntity<>(batch, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBatchWithoutBox(@RequestBody BatchWithoutBoxRequestDTO batchWithoutBoxRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            BatchWithoutBox createdBatch = batchWithoutBoxService.createBatchWithoutBox(batchWithoutBoxRequestDTO);
            return new ResponseEntity<>(createdBatch, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/{prodid}")
    public ResponseEntity<?> addBatchWithoutBoxByProduction(@PathVariable Long prodid, @RequestBody BatchWithoutBoxRequestDTO batchWithoutBoxRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            BatchWithoutBox batch = batchWithoutBoxService.createByProdid(prodid, batchWithoutBoxRequestDTO);
            return new ResponseEntity<>(batch, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{batchid}")
    public ResponseEntity<?> updateBatchWithoutBox(@PathVariable Long batchid, @RequestBody BatchWithoutBoxRequestDTO batchWithoutBoxRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            BatchWithoutBox batch = batchWithoutBoxService.updateBatchWithoutBox(batchid, batchWithoutBoxRequestDTO);
            return new ResponseEntity<>(batch, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{batchid}")
    public ResponseEntity<?> deleteBatchWithoutBox(@PathVariable Long batchid, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");
        try {
            batchWithoutBoxService.deleteBatchWithoutBox(batchid);
            return new ResponseEntity<>("Batch without box deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
