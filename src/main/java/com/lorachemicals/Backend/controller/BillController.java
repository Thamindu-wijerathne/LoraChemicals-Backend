package com.lorachemicals.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.BillRequestDTO;
import com.lorachemicals.Backend.dto.BillResponseDTO;
import com.lorachemicals.Backend.model.Bill;
import com.lorachemicals.Backend.services.BillService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bill")
public class BillController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createBill(@PathVariable Long id, @RequestBody BillRequestDTO data, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep");
        logger.error("Error creating bill {}", data);

        try {

            Bill bill = billService.createBill(id, data);

            // Convert to ResponseDTO
            BillResponseDTO response = new BillResponseDTO();
            response.setBillid(bill.getBillid());
            response.setTotal(bill.getTotal());
            response.setDatetime(bill.getDatetime());
            response.setSalesRepId(bill.getSalesRep().getSrepid());
            response.setSalesRepName(bill.getSalesRep().getUser().getName());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Error creating bill", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/salesrep-created-orders/{id}")
    public ResponseEntity<?> getSalesrepCreatedOrders(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep");
        try {
            List<Bill> bills = billService.getSalesrepBill(id);
            List<BillResponseDTO> response = bills.stream()
                    .map(billService::convertToDTO)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/get-total-orders")
    public ResponseEntity<?> getOrderDistrictCount(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            List<Bill> bills = billService.getAllBills();
            List<BillResponseDTO> response = bills.stream()
                    .map(billService::convertToDTO)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> getBillById(@PathVariable Long billId, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin", "warehouse");
        try {
            BillResponseDTO response = billService.getBillById(billId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Error fetching bill by ID: {}", billId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Bill not found: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error fetching bill by ID: {}", billId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }


}
