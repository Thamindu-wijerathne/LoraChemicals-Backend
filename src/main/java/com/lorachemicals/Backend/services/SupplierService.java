package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Supplier;
import com.lorachemicals.Backend.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;


@Service
public class SupplierService {

    private final SupplierRepository supplierRepo;

    public SupplierService(SupplierRepository supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    // Create
    public Supplier addSupplier(Supplier supplier) {

        supplier.setStatus("1");
        return supplierRepo.save(supplier);
    }

    // Read all
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll().stream()
                .filter(s -> "1".equals(s.getStatus()))
                .collect(Collectors.toList());
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

            return supplierRepo.save(existing);
        } else {
            return null;
        }
    }

    // Delete
    public boolean deleteSupplier(Long id) {
        Optional<Supplier> optionalSupplier = supplierRepo.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier supplier = optionalSupplier.get();
            supplier.setStatus("0"); // mark as deleted
            supplierRepo.save(supplier);
            return true;
        }
        return false;
    }

}

