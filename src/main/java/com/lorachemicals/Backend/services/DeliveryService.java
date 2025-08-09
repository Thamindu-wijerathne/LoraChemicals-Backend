package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchInventoryDeliveryResponseDTO;
import com.lorachemicals.Backend.dto.DeliveryRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryResponseDTO;
import com.lorachemicals.Backend.model.BatchInventory;
import com.lorachemicals.Backend.model.BatchInventoryDelivery;
import com.lorachemicals.Backend.model.BatchInventoryDeliveryId;
import com.lorachemicals.Backend.model.BatchInventoryWithoutBox;
import com.lorachemicals.Backend.model.BatchType;
import com.lorachemicals.Backend.model.BatchTypeWithoutBox;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.model.Delivery;
import com.lorachemicals.Backend.model.ParentBatchType;
import com.lorachemicals.Backend.model.Route;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.model.Vehicle;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.repository.BatchInventoryDeliveryRepository;
import com.lorachemicals.Backend.repository.BatchInventoryRepository;
import com.lorachemicals.Backend.repository.BatchInventoryWithoutBoxRepository;
import com.lorachemicals.Backend.repository.BatchTypeRepository;
import com.lorachemicals.Backend.repository.BatchTypeWithoutBoxRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.DeliveryRepository;
import com.lorachemicals.Backend.repository.RouteRepository;
import com.lorachemicals.Backend.repository.SalesRepRepository;
import com.lorachemicals.Backend.repository.VehicleRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;

import jakarta.transaction.Transactional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private BatchInventoryDeliveryRepository batchInventoryDeliveryRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BatchTypeRepository batchTypeRepository;

    @Autowired
    private BatchTypeWithoutBoxRepository batchTypeWithoutBoxRepository;

    @Autowired
    private WarehouseManagerRepository warehouseManagerRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private BatchInventoryRepository batchInventoryRepository;

    @Autowired
    private BatchInventoryWithoutBoxRepository batchInventoryWithoutBoxRepository;

    @Transactional
    public DeliveryResponseDTO createDelivery(DeliveryRequestDTO requestDTO) {
        try {
            Delivery delivery = new Delivery();
            if (requestDTO.getDispatchdate() != null && !requestDTO.getDispatchdate().isEmpty()) {
                String dateStr = requestDTO.getDispatchdate();
                if (dateStr.endsWith("Z")) {
                    dateStr = dateStr.substring(0, dateStr.length() - 1);
                }
                if (dateStr.contains(".")) {
                    dateStr = dateStr.substring(0, dateStr.lastIndexOf('.'));
                }
                delivery.setDispatchdate(java.time.LocalDateTime.parse(dateStr));
            } else {
                delivery.setDispatchdate(java.time.LocalDateTime.now());
            }
            
            if (requestDTO.getCamedate() != null && !requestDTO.getCamedate().isEmpty()) {
                String dateStr = requestDTO.getCamedate();
                if (dateStr.endsWith("Z")) {
                    dateStr = dateStr.substring(0, dateStr.length() - 1);
                }
                if (dateStr.contains(".")) {
                    dateStr = dateStr.substring(0, dateStr.lastIndexOf('.'));
                }
                delivery.setCamedate(java.time.LocalDateTime.parse(dateStr));
            }
            
            delivery.setStatus(requestDTO.getStatus());

            SalesRep salesRep = salesRepRepository.findById(requestDTO.getSrepid())
                    .orElseThrow(() -> new RuntimeException("SalesRep not found"));
            delivery.setSalesRep(salesRep);

            Route route = routeRepository.findById(requestDTO.getRouteid())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            delivery.setRoute(route);

            Vehicle vehicle = vehicleRepository.findById(requestDTO.getVehicleid())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));
            delivery.setVehicle(vehicle);

            salesRep.setStatus(0);
            salesRepRepository.save(salesRep);            
            vehicle.setStatus("0");
            vehicleRepository.save(vehicle);
            
            Delivery savedDelivery = deliveryRepository.save(delivery);
            
            if (requestDTO.getBatchInventoryDetails() != null && !requestDTO.getBatchInventoryDetails().isEmpty()) {
                System.out.println("📦 PROCESSING " + requestDTO.getBatchInventoryDetails().size() + " BATCH INVENTORY DETAILS:");
                
                for (DeliveryRequestDTO.BatchInventoryDetail batchDetail : requestDTO.getBatchInventoryDetails()) {
                    BatchInventoryDelivery batchDelivery = new BatchInventoryDelivery();
                    Long batchTypeId = null;
                    ParentBatchType parentBatchType = null;
                    
                    if ("with_box".equals(batchDetail.getInventoryType())) {
                        // Look in regular BatchType table for with_box items
                        BatchType batchType = batchTypeRepository.findById(batchDetail.getBatchtypeid())
                                .orElseThrow(() -> new RuntimeException("BatchType not found for ID: " + batchDetail.getBatchtypeid()));
                        System.out.println("   📦 Found BatchType (with box): " + batchType.getBatchtypename() + " (ID: " + batchType.getBatchtypeid() + ")");
                        batchTypeId = batchType.getBatchtypeid();
                        parentBatchType = batchType;
                    } else if ("without_box".equals(batchDetail.getInventoryType())) {
                        // Look in BatchTypeWithoutBox table for without_box items
                        BatchTypeWithoutBox batchTypeWithoutBox = batchTypeWithoutBoxRepository.findById(batchDetail.getBatchtypeid())
                                .orElseThrow(() -> new RuntimeException("BatchTypeWithoutBox not found for ID: " + batchDetail.getBatchtypeid()));
                        System.out.println("   📦 Found BatchTypeWithoutBox: " + batchTypeWithoutBox.getBatchtypename() + " (ID: " + batchTypeWithoutBox.getId() + ")");
                        batchTypeId = batchTypeWithoutBox.getId();
                        parentBatchType = batchTypeWithoutBox;
                    } else {
                        throw new RuntimeException("Unknown inventory type: " + batchDetail.getInventoryType());
                    }
                    BatchInventoryDeliveryId batchId = new BatchInventoryDeliveryId(
                            batchTypeId,
                            savedDelivery.getDeliveryid(),
                            java.time.LocalDateTime.now(),
                            batchDetail.getType()
                    );
                    batchDelivery.setId(batchId);
                    
                    // Set relationships
                    batchDelivery.setBatchType(parentBatchType);
                    batchDelivery.setDelivery(savedDelivery);
                    
                    // Set warehouse manager
                    WarehouseManager warehouseManager = warehouseManagerRepository.findById(batchDetail.getWmid())
                            .orElseThrow(() -> new RuntimeException("WarehouseManager not found: " + batchDetail.getWmid()));
                    System.out.println("✅ Found WarehouseManager: " + warehouseManager.getWmid());
                    batchDelivery.setWarehouseManager(warehouseManager);
                    
                    // Set quantities
                    System.out.println("   🔢 Setting delivery record quantities - Quantity: " + batchDetail.getQuantity() + ", CurrentQuantity: " + batchDetail.getCurrentQuantity());
                    batchDelivery.setQuantity(batchDetail.getQuantity());
                    batchDelivery.setCurrentQuantity(batchDetail.getCurrentQuantity());
                    
                    // Save batch delivery record
                    batchInventoryDeliveryRepository.save(batchDelivery);
                    System.out.println("   ✅ BatchInventoryDelivery record created for " + batchDetail.getType().toUpperCase() + " type with quantity: " + batchDetail.getQuantity());
                    
                    // Deduct inventory from the specific inventory record
                    if ("orders".equals(batchDetail.getType()) || "extras".equals(batchDetail.getType())) {   
                        boolean deductionSuccessful = false;
                        
                        // Deduct from the specific inventory record based on type
                        if ("with_box".equals(batchDetail.getInventoryType())) {
                            // Deduct from BatchInventory
                            BatchInventory inventory = batchInventoryRepository.findById(batchDetail.getInventoryid()).orElse(null);
                            if (inventory != null) {
                                int availableBatches = inventory.getBatch_quantity();
                                if (availableBatches >= batchDetail.getQuantity()) {
                                    int newBatchQuantity = availableBatches - batchDetail.getQuantity();
                                    inventory.setBatch_quantity(newBatchQuantity);
                                    batchInventoryRepository.save(inventory);
                                    deductionSuccessful = true;
                                    
                                    System.out.println("   ✅ BatchInventory (ID: " + inventory.getInventoryid() + ") updated: " + 
                                        availableBatches + " batches -> " + newBatchQuantity + " batches (-" + batchDetail.getQuantity() + " batches)");
                                } else {
                                    throw new RuntimeException("Insufficient inventory in BatchInventory ID: " + batchDetail.getInventoryid() + 
                                        ". Available: " + availableBatches + ", Required: " + batchDetail.getQuantity());
                                }
                            } else {
                                throw new RuntimeException("BatchInventory not found for ID: " + batchDetail.getInventoryid());
                            }
                        } else if ("without_box".equals(batchDetail.getInventoryType())) {
                            // Deduct from BatchInventoryWithoutBox
                            BatchInventoryWithoutBox inventory = batchInventoryWithoutBoxRepository.findById(batchDetail.getInventoryid()).orElse(null);
                            if (inventory != null) {
                                int availableBatches = inventory.getBatch_quantity();
                                if (availableBatches >= batchDetail.getQuantity()) {
                                    int newBatchQuantity = availableBatches - batchDetail.getQuantity();
                                    inventory.setBatch_quantity(newBatchQuantity);
                                    batchInventoryWithoutBoxRepository.save(inventory);
                                    deductionSuccessful = true;
                                    
                                    System.out.println("   ✅ BatchInventoryWithoutBox (ID: " + inventory.getId() + ") updated: " + 
                                        availableBatches + " batches -> " + newBatchQuantity + " batches (-" + batchDetail.getQuantity() + " batches)");
                                } else {
                                    throw new RuntimeException("Insufficient inventory in BatchInventoryWithoutBox ID: " + batchDetail.getInventoryid() + 
                                        ". Available: " + availableBatches + ", Required: " + batchDetail.getQuantity());
                                }
                            } else {
                                throw new RuntimeException("BatchInventoryWithoutBox not found for ID: " + batchDetail.getInventoryid());
                            }
                        } else {
                            throw new RuntimeException("Unknown inventory type: " + batchDetail.getInventoryType());
                        }
                        
                        if (deductionSuccessful) {
                            System.out.println("   ✅ Successfully deducted " + batchDetail.getQuantity() + " batches from " + 
                                batchDetail.getInventoryType() + " inventory ID: " + batchDetail.getInventoryid());
                            System.out.println("   📊 SUMMARY: DeliveryRecord.quantity=" + batchDetail.getQuantity() + 
                                ", DeductedFromInventory=" + batchDetail.getQuantity() + " ✅ MATCH");
                        }
                    }
                }
            }
            
            // Update selected orders status to "ongoing" 
            if (requestDTO.getSelectedOrders() != null && !requestDTO.getSelectedOrders().isEmpty()) {
                System.out.println("📋 UPDATING " + requestDTO.getSelectedOrders().size() + " ORDERS TO 'ONGOING' STATUS:");
                for (DeliveryRequestDTO.SelectedOrder selectedOrder : requestDTO.getSelectedOrders()) {
                    CustomerOrder order = customerOrderRepository.findById(selectedOrder.getOrderid())
                            .orElseThrow(() -> new RuntimeException("Order not found: " + selectedOrder.getOrderid()));
                    order.setStatus("ongoing");
                    customerOrderRepository.save(order);
                    System.out.println("   ✅ Order " + selectedOrder.getOrderid() + " status updated to 'ongoing'");
                }
            }
            return convertToResponseDTO(savedDelivery);
        } catch (RuntimeException e) {
            System.err.println("❌ Runtime error in DeliveryService.createDelivery: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in DeliveryService.createDelivery: " + e.getMessage());
            throw new RuntimeException("Failed to create delivery: " + e.getMessage(), e);
        }
    }

    public List<DeliveryResponseDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<DeliveryResponseDTO> getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public List<DeliveryResponseDTO> getDeliveriesBySalesRep(Long srepid) {
        return deliveryRepository.findBySalesRep_Srepid(srepid).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DeliveryResponseDTO> getDeliveriesByRoute(Long routeid) {
        return deliveryRepository.findByRoute_Routeid(routeid).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DeliveryResponseDTO> getDeliveriesByStatus(int status) {
        return deliveryRepository.findByStatus(status).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private DeliveryResponseDTO convertToResponseDTO(Delivery delivery) {
        DeliveryResponseDTO responseDTO = new DeliveryResponseDTO();
        responseDTO.setDeliveryid(delivery.getDeliveryid());
        responseDTO.setDispatchdate(delivery.getDispatchdate());
        responseDTO.setCamedate(delivery.getCamedate());
        responseDTO.setStatus(delivery.getStatus());
        responseDTO.setSrepid(delivery.getSalesRep().getSrepid());
        responseDTO.setSalesRepName(delivery.getSalesRep().getUser().getName());
        responseDTO.setRouteid(delivery.getRoute().getRouteid());
        responseDTO.setRouteName(delivery.getRoute().getDistrict());
        responseDTO.setVehicleid(delivery.getVehicle().getId());
        responseDTO.setVehicleNumber(delivery.getVehicle().getVehicleNo());

        // Get batch inventory items for this delivery
        List<BatchInventoryDelivery> batchItems = batchInventoryDeliveryRepository.findById_Deliveryid(delivery.getDeliveryid());
        List<BatchInventoryDeliveryResponseDTO> batchItemDTOs = batchItems.stream()
                .map(this::convertToBatchInventoryResponseDTO)
                .collect(Collectors.toList());
        responseDTO.setBatchInventoryItems(batchItemDTOs);

        return responseDTO;
    }

    private BatchInventoryDeliveryResponseDTO convertToBatchInventoryResponseDTO(BatchInventoryDelivery batchItem) {
        BatchInventoryDeliveryResponseDTO dto = new BatchInventoryDeliveryResponseDTO();
        dto.setBatchtypeid(batchItem.getId().getBatchtypeid());
        dto.setBatchTypeName(batchItem.getBatchType().getBatchtypename());
        dto.setDeliveryid(batchItem.getId().getDeliveryid());
        dto.setDatetime(batchItem.getId().getDatetime());
        dto.setType(batchItem.getId().getType());
        dto.setQuantity(batchItem.getQuantity());
        dto.setCurrentQuantity(batchItem.getCurrentQuantity());
        dto.setWmid(batchItem.getWarehouseManager().getWmid());
        dto.setWarehouseManagerName(batchItem.getWarehouseManager().getUser().getName());
        return dto;
    }
}
