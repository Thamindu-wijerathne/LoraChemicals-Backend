package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.DamageReportRequestDTO;
import com.lorachemicals.Backend.dto.DamageReportResponseDTO;
import com.lorachemicals.Backend.model.DamageReport;
import com.lorachemicals.Backend.services.DamageReportService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@RestController
@RequestMapping("/damage-report")
public class DamageReportController {

    private final DamageReportService service;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public DamageReportController(DamageReportService service) {
        this.service = service;
    }

    // ✅ Create a new damage report (multipart/form-data support)
    @PostMapping("/add")
    public ResponseEntity<?> createReport(@RequestBody DamageReportRequestDTO data, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse"); // Adjust roles if needed
        try {
            logger.info("adding Damage report come from request dto :  {}", data);
            DamageReportResponseDTO created = service.createReport(data);
            logger.info("adding Damage report :  {}", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create damage report");
        }
    }

    // ✅ Get all reports
    @GetMapping("/all")
    public ResponseEntity<?> getAllReports(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin", "warehouse");
        logger.info("Get /damage-report called");
        try {
            List<DamageReportResponseDTO> list = service.getAllReports();
            logger.info("damge report list: {}", list);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch reports");
        }
    }

    // ✅ Admin reviews (approve or reject)
    @PutMapping("/review/{id}")
    public ResponseEntity<?> reviewReport(
            @PathVariable Long id,
            @RequestParam String action,
            HttpServletRequest request) {

        AccessControlUtil.checkAccess(request, "admin");

        try {
            DamageReportResponseDTO updated = service.reviewReport(id, action);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to review report");
        }
    }

    // ✅ Get report by ID
//    @GetMapping("/byid/{id}")
//    public ResponseEntity<?> getReportById(@PathVariable Long id, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "admin", "warehouse");
//        try {
////            DamageReportResponseDTO dto = service.getReportById(id);
//            return ResponseEntity.ok(dto);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    // ✅ Update report
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateReport(
//            @PathVariable Long id,
//            @RequestBody DamageReportRequestDTO dto,
//            HttpServletRequest request
//    ) {
//        AccessControlUtil.checkAccess(request, "admin");
//        try {
//            DamageReportResponseDTO updated = service.updateReport(id, dto);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    // ✅ Delete report
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteReport(@PathVariable Long id, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "admin");
//        try {
//            service.deleteReport(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
}
