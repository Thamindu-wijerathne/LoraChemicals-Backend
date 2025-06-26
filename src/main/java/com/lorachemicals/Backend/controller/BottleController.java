package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.BottleRequestDTO;
import com.lorachemicals.Backend.dto.BottleResponseDTO;
import com.lorachemicals.Backend.services.BottleService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bottle")
@CrossOrigin(origins = "*") // optional, based on frontend
public class BottleController {

    @Autowired
    private BottleService bottleService;

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody BottleRequestDTO bottleRequestDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse");

        try {
            BottleResponseDTO response = bottleService.createBottle(bottleRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
