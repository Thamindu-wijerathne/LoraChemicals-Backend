//package com.lorachemicals.Backend.controller;
//
//import com.lorachemicals.Backend.model.Supplier;
//import com.lorachemicals.Backend.services.SupplierService;
//import com.lorachemicals.Backend.util.AccessControlUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/suppliers")
//public class SupplierController {
//
//    private final SupplierService supplierService;
//
//    @Autowired
//    public SupplierController(SupplierService supplierService) {
//        this.supplierService = supplierService;
//    }
//
//    // GET: Get all suppliers
//    @GetMapping("/all")
//    public ResponseEntity<List<Supplier>> getAllSuppliers(HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "warehouse");
//        return ResponseEntity.ok(supplierService.getAllSuppliers());
//    }
//
//    // POST: Add new supplier
//    @PostMapping("/add")
//    public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "warehouse");
//        Supplier saved = supplierService.addSupplier(supplier);
//        return ResponseEntity.ok(saved);
//    }
//
//    // PUT: Update supplier by ID
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "warehouse");
//
//        Supplier updated = supplierService.updateSupplier(id, supplier);
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
//        }
//    }
//
//    // DELETE: Delete supplier by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteSupplier(@PathVariable Long id, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "warehouse");
//
//        boolean deleted = supplierService.deleteSupplier(id);
//        if (deleted) {
//            return ResponseEntity.ok("Supplier deleted successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
//        }
//    }
//}

        package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.SupplierDTO;
import com.lorachemicals.Backend.model.Supplier;
import com.lorachemicals.Backend.services.SupplierService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // GET: Get all suppliers
    @GetMapping("/all")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");

        List<Supplier> suppliers = supplierService.getAllSuppliers();

        List<SupplierDTO> dtos = suppliers.stream()
                .map(s -> {
                    SupplierDTO dto = new SupplierDTO();
                    dto.setSupplierid(s.getSupplierid());
                    dto.setFirstName(s.getFirstName());
                    dto.setLastName(s.getLastName());
                    dto.setBrId(s.getBrId());
                    dto.setPhone(s.getPhone());
                    dto.setAddress(s.getAddress());
                    dto.setEmail(s.getEmail());
                    dto.setSupplierType(s.getSupplierType());

                    return dto;
                })
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // POST: Add new supplier
    @PostMapping("/add")
    public ResponseEntity<SupplierDTO> addSupplier(@RequestBody SupplierDTO supplierDto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");

        Supplier supplier = new Supplier();
        supplier.setSupplierid(supplierDto.getSupplierid());
        supplier.setFirstName(supplierDto.getFirstName());
        supplier.setLastName(supplierDto.getLastName());
        supplier.setBrId(supplierDto.getBrId());
        supplier.setPhone(supplierDto.getPhone());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setSupplierType(supplierDto.getSupplierType());

        Supplier saved = supplierService.addSupplier(supplier);

        SupplierDTO savedDto = new SupplierDTO();
        savedDto.setSupplierid(saved.getSupplierid());
        savedDto.setFirstName(saved.getFirstName());
        savedDto.setLastName(saved.getLastName());
        savedDto.setBrId(saved.getBrId());
        savedDto.setPhone(saved.getPhone());
        savedDto.setAddress(saved.getAddress());
        savedDto.setEmail(saved.getEmail());
        savedDto.setSupplierType(saved.getSupplierType());

        return ResponseEntity.ok(savedDto);
    }

    // PUT: Update supplier by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");

        Supplier supplier = new Supplier();
        supplier.setSupplierid(supplierDto.getSupplierid());
        supplier.setFirstName(supplierDto.getFirstName());
        supplier.setLastName(supplierDto.getLastName());
        supplier.setBrId(supplierDto.getBrId());
        supplier.setPhone(supplierDto.getPhone());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setSupplierType(supplierDto.getSupplierType());

        Supplier updated = supplierService.updateSupplier(id, supplier);
        if (updated != null) {
            SupplierDTO updatedDto = new SupplierDTO();
            updatedDto.setSupplierid(updated.getSupplierid());
            updatedDto.setFirstName(updated.getFirstName());
            updatedDto.setLastName(updated.getLastName());
            updatedDto.setBrId(updated.getBrId());
            updatedDto.setPhone(updated.getPhone());
            updatedDto.setAddress(updated.getAddress());
            updatedDto.setEmail(updated.getEmail());
            updatedDto.setSupplierType(updated.getSupplierType());

            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }
    }

    // DELETE: Delete supplier by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "warehouse", "admin");

        boolean deleted = supplierService.deleteSupplier(id);
        if (deleted) {
            return ResponseEntity.ok("Supplier deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }
    }
}
