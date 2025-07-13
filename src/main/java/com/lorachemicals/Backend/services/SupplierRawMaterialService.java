package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.PurchaseWithInventoryUpdateDTO;
import com.lorachemicals.Backend.dto.SupplierRawMaterialRequestDTO;
import com.lorachemicals.Backend.dto.SupplierRawMaterialResponseDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private RawChemicalRepository rawChemicalRepository;

    @Autowired
    private BottleRepository bottleRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Transactional
    public void createPurchaseAndUpdateInventory(PurchaseWithInventoryUpdateDTO dto) {
        try {
            // Step 1: Create SupplierRawMaterial
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + dto.getSupplierId()));

            RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getInventoryId())
                    .orElseThrow(() -> new RuntimeException("RawMaterial not found with ID: " + dto.getInventoryId()));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWarehouseManagerId())
                    .orElseThrow(() -> new RuntimeException("WarehouseManager not found with ID: " + dto.getWarehouseManagerId()));

            SupplierRawMaterialId id = new SupplierRawMaterialId(
                    dto.getInventoryId(), dto.getSupplierId(), dto.getDate());

            SupplierRawMaterial srm = new SupplierRawMaterial();
            srm.setId(id);
            srm.setSupplier(supplier);
            srm.setRawMaterial(rawMaterial);
            srm.setWarehouseManager(wm);
            srm.setExpDate(dto.getExpDate());
            srm.setQuantity(dto.getQuantity());
            srm.setCurrentQuantity(dto.getQuantity());
            srm.setUnitPrice(dto.getUnitPrice());
            srm.setTotalPrice(dto.getTotalPrice());

            supplierRawMaterialRepository.save(srm);

            // Step 2: Update inventory (dynamic type)
            String type = getRawMaterialType(rawMaterial);

            switch (type) {
                case "bottle" -> {
                    try {
                        Bottle bottle = bottleRepository.findById(dto.getInventoryId())
                                .orElseThrow(() -> new RuntimeException("Bottle not found with ID: " + dto.getInventoryId()));
                        bottle.setQuantity(bottle.getQuantity() + dto.getQuantity());
                        bottleRepository.save(bottle);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to update bottle inventory: " + e.getMessage(), e);
                    }
                }
                case "chemical" -> {
                    try {
                        RawChemical chemical = rawChemicalRepository.findById(dto.getInventoryId())
                                .orElseThrow(() -> new RuntimeException("Chemical not found with ID: " + dto.getInventoryId()));
                        chemical.setVolume(chemical.getVolume() + dto.getQuantity());
                        rawChemicalRepository.save(chemical);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to update chemical inventory: " + e.getMessage(), e);
                    }
                }
                case "label" -> {
                    try {
                        Label label = labelRepository.findById(dto.getInventoryId())
                                .orElseThrow(() -> new RuntimeException("Label not found with ID: " + dto.getInventoryId()));
                        label.setQuantity(label.getQuantity() + dto.getQuantity());
                        labelRepository.save(label);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to update label inventory: " + e.getMessage(), e);
                    }
                }
                case "box" -> {
                    try {
                        Box box = boxRepository.findById(dto.getInventoryId())
                                .orElseThrow(() -> new RuntimeException("Box not found with ID: " + dto.getInventoryId()));
                        box.setQuantity(box.getQuantity() + dto.getQuantity());
                        boxRepository.save(box);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to update box inventory: " + e.getMessage(), e);
                    }
                }
                default -> throw new RuntimeException("Unknown inventory type for inventory ID: " + dto.getInventoryId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create purchase and update inventory: " + e.getMessage(), e);
        }
    }


    private String getRawMaterialType(RawMaterial rawMaterial) {
        if (rawChemicalRepository.existsById(rawMaterial.getInventoryid())) {
            return "chemical";
        } else if (bottleRepository.existsById(rawMaterial.getInventoryid())) {
            return "bottle";
        } else if (labelRepository.existsById(rawMaterial.getInventoryid())) {
            return "label";
        } else if (boxRepository.existsById(rawMaterial.getInventoryid())) {
            return "box";
        }
        return "unknown";
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
    public SupplierRawMaterialResponseDTO getById(Long inventoryId, Long supplierId, LocalDateTime date) {
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
    public void deleteById(Long inventoryId, Long supplierId, LocalDateTime date) {
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
    public SupplierRawMaterialResponseDTO updateById(Long inventoryId, Long supplierId, LocalDateTime date, SupplierRawMaterialRequestDTO dto) {
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
    public SupplierRawMaterialResponseDTO updateCQuantity(Long inventoryId, Long supplierId, LocalDateTime date, Integer quantity) {
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

    // CREATE
//    public SupplierRawMaterialResponseDTO create(SupplierRawMaterialRequestDTO dto) {
//        try {
//            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
//                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
//
//            RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getInventoryId())
//                    .orElseThrow(() -> new RuntimeException("RawMaterial not found"));
//
//            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWarehouseManagerId())
//                    .orElseThrow(() -> new RuntimeException("WarehouseManager not found"));
//
//            SupplierRawMaterialId id = new SupplierRawMaterialId(dto.getInventoryId(), dto.getSupplierId(), dto.getDate());
//
//            SupplierRawMaterial srm = new SupplierRawMaterial();
//            srm.setId(id);
//            srm.setSupplier(supplier);
//            srm.setRawMaterial(rawMaterial);
//            srm.setWarehouseManager(wm);
//            srm.setExpDate(dto.getExpDate());
//            srm.setQuantity(dto.getQuantity());
//            srm.setUnitPrice(dto.getUnitPrice());
//            srm.setCurrentQuantity(dto.getQuantity());
//            srm.setTotalPrice(dto.getUnitPrice() * dto.getQuantity());
//
//            supplierRawMaterialRepository.save(srm);
//            return convertToDTO(srm);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create SupplierRawMaterial: " + e.getMessage());
//        }
//    }

}
