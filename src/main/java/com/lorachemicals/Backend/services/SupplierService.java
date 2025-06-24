//// Purpose: Contains business logic related to Supplier operations.
//
//package com.lorachemicals.Backend.services;
//
//import com.lorachemicals.Backend.model.Supplier;
//import com.lorachemicals.Backend.repository.SupplierRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class SupplierService {
//
//    private final SupplierRepository supplierRepo;
//
//    public SupplierService(SupplierRepository supplierRepo) {
//        this.supplierRepo = supplierRepo;
//    }
//
//    public Supplier addSupplier(Supplier supplier) {
//        return supplierRepo.save(supplier);
//    }
//
//    public List<Supplier> getAllSuppliers() {
//        return supplierRepo.findAll();
//    }
//
//    public Supplier getSupplierById(Long id) {
//        return supplierRepo.findById(id).orElse(null);
//    }
//
//    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
//        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
//        if (optionalSupplier.isPresent()) {
//            Supplier existing = optionalSupplier.get();
//            existing.setName(updatedSupplier.getName());
//            existing.setEmail(updatedSupplier.getEmail());
//            existing.setPhone(updatedSupplier.getPhone());
//            existing.setAddress(updatedSupplier.getAddress());
//            return supplierRepo.save(existing);
//        } else {
//            return null;
//        }
//    }
//
//    public boolean deleteSupplier(Long id) {
//        if (supplierRepo.existsById(id)) {
//            supplierRepo.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//}

package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Supplier;
import com.lorachemicals.Backend.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepo;

    public SupplierService(SupplierRepository supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    // Create
    public Supplier addSupplier(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    // Read all
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }

    // Read by ID
    public Supplier getSupplierById(Long id) {
        return supplierRepo.findById(id).orElse(null);
    }

    // Update
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier existing = optionalSupplier.get();
            existing.setFirstName(updatedSupplier.getFirstName());
            existing.setLastName(updatedSupplier.getLastName());
            existing.setBrId(updatedSupplier.getBrId());
            existing.setEmail(updatedSupplier.getEmail());
            existing.setPhone(updatedSupplier.getPhone());
            existing.setAddress(updatedSupplier.getAddress());
            existing.setSupplierType(updatedSupplier.getSupplierType());
            existing.setStatus(updatedSupplier.getStatus());
            return supplierRepo.save(existing);
        } else {
            return null;
        }
    }

    // Delete
    public boolean deleteSupplier(Long id) {
        if (supplierRepo.existsById(id)) {
            supplierRepo.deleteById(id);
            return true;
        }
        return false;
    }
}

