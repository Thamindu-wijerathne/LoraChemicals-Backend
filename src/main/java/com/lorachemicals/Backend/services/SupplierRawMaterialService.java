package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.SupplierRawMaterialRequestDTO;
import com.lorachemicals.Backend.dto.SupplierRawMaterialResponseDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierRawMaterialService {

    @Autowired
    private SupplierRawMaterialRepository supplierRawMaterialRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private WarehouseManagerRepository warehouseManagerRepository;

    // CREATE
    public SupplierRawMaterialResponseDTO create(SupplierRawMaterialRequestDTO dto) {
        try {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));

            RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getInventoryId())
                    .orElseThrow(() -> new RuntimeException("RawMaterial not found"));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWarehouseManagerId())
                    .orElseThrow(() -> new RuntimeException("WarehouseManager not found"));

            SupplierRawMaterialId id = new SupplierRawMaterialId(dto.getInventoryId(), dto.getSupplierId(), dto.getDate());

            SupplierRawMaterial srm = new SupplierRawMaterial();
            srm.setId(id);
            srm.setSupplier(supplier);
            srm.setRawMaterial(rawMaterial);
            srm.setWarehouseManager(wm);
            srm.setExpDate(dto.getExpDate());
            srm.setQuantity(dto.getQuantity());
            srm.setUnitPrice(dto.getUnitPrice());
            srm.setCurrentQuantity(dto.getQuantity());
            srm.setTotalPrice(dto.getUnitPrice() * dto.getQuantity());

            supplierRawMaterialRepository.save(srm);
            return convertToDTO(srm);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SupplierRawMaterial: " + e.getMessage());
        }
    }

    // GET ALL
    public List<SupplierRawMaterialResponseDTO> getAll() {
        try {
            return supplierRawMaterialRepository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve SupplierRawMaterials: " + e.getMessage());
        }
    }

    //get all by arranged latest exp date first
    public List<SupplierRawMaterialResponseDTO> getAllByexp() {
        try {
            return supplierRawMaterialRepository.findAllByOrderByExpDateAsc()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve SupplierRawMaterials: " + e.getMessage());
        }
    }

    //get all by exp and chemid
    public List<SupplierRawMaterialResponseDTO> getByChemIdOrderByExp(Long chemid) {
        try {
            return supplierRawMaterialRepository.findByChemicalIdOrderByExpAsc(chemid)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve SupplierRawMaterials: " + e.getMessage());
        }
    }

    // GET BY ID
    public SupplierRawMaterialResponseDTO getById(Long inventoryId, Long supplierId, LocalDate date) {
        try {
            SupplierRawMaterialId id = new SupplierRawMaterialId(inventoryId, supplierId, date);
            SupplierRawMaterial srm = supplierRawMaterialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Record not found"));
            return convertToDTO(srm);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve record: " + e.getMessage());
        }
    }


    // DELETE BY ID
    public void deleteById(Long inventoryId, Long supplierId, LocalDate date) {
        try {
            SupplierRawMaterialId id = new SupplierRawMaterialId(inventoryId, supplierId, date);
            if (!supplierRawMaterialRepository.existsById(id)) {
                throw new RuntimeException("Record not found");
            }
            supplierRawMaterialRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete record: " + e.getMessage());
        }
    }

    // UPDATE BY ID
    public SupplierRawMaterialResponseDTO updateById(Long inventoryId, Long supplierId, LocalDate date, SupplierRawMaterialRequestDTO dto) {
        try {
            SupplierRawMaterialId id = new SupplierRawMaterialId(inventoryId, supplierId, date);
            SupplierRawMaterial srm = supplierRawMaterialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Record not found"));

            srm.setExpDate(dto.getExpDate());
            srm.setQuantity(dto.getQuantity());
            srm.setUnitPrice(dto.getUnitPrice());
            srm.setTotalPrice(dto.getQuantity() * dto.getUnitPrice());
            srm.setCurrentQuantity(dto.getCurrentQuantity());

            supplierRawMaterialRepository.save(srm);
            return convertToDTO(srm);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update record: " + e.getMessage());
        }
    }

    //update c.quantity
    public SupplierRawMaterialResponseDTO updateCQuantity(Long inventoryId, Long supplierId, LocalDate date, Integer quantity) {
        try{
            SupplierRawMaterialId id = new SupplierRawMaterialId(inventoryId, supplierId, date);
            SupplierRawMaterial srm = supplierRawMaterialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Record not found"));

            srm.setCurrentQuantity(quantity);
            supplierRawMaterialRepository.save(srm);
            return convertToDTO(srm);
        } catch (Exception e){
            throw new RuntimeException("Failed to update record: " + e.getMessage());
        }
    }

    // Mapper method
    private SupplierRawMaterialResponseDTO convertToDTO(SupplierRawMaterial entity) {
        SupplierRawMaterialResponseDTO dto = new SupplierRawMaterialResponseDTO();
        dto.setInventoryId(entity.getRawMaterial().getInventoryid());
        dto.setRawMaterialInventoryId(entity.getRawMaterial().getInventoryid());  // Use inventoryId as identifier

        dto.setSupplierId(entity.getSupplier().getSupplierid());
        dto.setSupplierName(entity.getSupplier().getName());
        dto.setDate(entity.getId().getDate());
        dto.setExpDate(entity.getExpDate());
        dto.setQuantity(entity.getQuantity());
        dto.setWarehouseManagerId(entity.getWarehouseManager().getWmid());
        dto.setWarehouseManagerName(entity.getWarehouseManager().getUser().getName());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setCurrentQuantity(entity.getCurrentQuantity());
        return dto;
    }

}
