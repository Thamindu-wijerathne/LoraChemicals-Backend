package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchInventoryDeliveryResponseDTO;
import com.lorachemicals.Backend.dto.DeliveryRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryResponseDTO;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class DeliveryService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

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

    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

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

            // 2. Loop orders and save into delivery_order table
            for (DeliveryRequestDTO.SelectedOrder orderDTO : requestDTO.getSelectedOrders()) {
                Long orderId = orderDTO.getOrderid();

                CustomerOrder order = customerOrderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

                DeliveryOrder deliveryOrder = new DeliveryOrder();
                deliveryOrder.setDelivery(savedDelivery);
                deliveryOrder.setCustomerOrder(order);

                // Set the composite key
                deliveryOrder.setId(new DeliveryOrderId(savedDelivery.getDeliveryid(), orderId));

                deliveryOrderRepository.save(deliveryOrder);
            }


            
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

    @Transactional
    public boolean updateDeliveryStatus(Long deliveryId, int newStatus) {
        logger.info("🔄 Starting delivery status update - ID: {}, New Status: {}", deliveryId, newStatus);

        try {
            if (deliveryId == null) {
                throw new RuntimeException("Delivery ID cannot be null");
            }

            Optional<Delivery> deliveryOpt = deliveryRepository.findById(deliveryId);
            if (deliveryOpt.isEmpty()) {
                throw new RuntimeException("Delivery not found with ID: " + deliveryId);
            }
            Delivery delivery = deliveryOpt.get();
            logger.info("✅ Found delivery: {} with current status: {}", delivery.getDeliveryid(), delivery.getStatus());

            if (newStatus == 0) { // Completing delivery, restore resources

                // 1. Set came date
                delivery.setCamedate(java.time.LocalDateTime.now());

                // 2. Update Sales Rep status to 1 (available)
                SalesRep salesRep = delivery.getSalesRep();
                if (salesRep != null) {
                    salesRep.setStatus(1);
                    salesRepRepository.save(salesRep);
                    logger.info("✅ Sales Rep {} status updated to available", salesRep.getSrepid());
                }

                // 3. Update Vehicle status to "1" (available)
                Vehicle vehicle = delivery.getVehicle();
                if (vehicle != null) {
                    vehicle.setStatus("1");
                    vehicleRepository.save(vehicle);
                    logger.info("✅ Vehicle {} status updated to available", vehicle.getId());
                }

                // 4. Restore batch inventory quantities
                List<BatchInventoryDelivery> batchItems = batchInventoryDeliveryRepository.findById_Deliveryid(deliveryId);
                logger.info("📦 Found {} batch items to restore for delivery ID: {}", batchItems.size(), deliveryId);

                for (BatchInventoryDelivery batchItem : batchItems) {
                    Long batchTypeId = batchItem.getId().getBatchtypeid();
                    int currentQuantity = batchItem.getCurrentQuantity();

                    logger.info("🔄 Processing batch item - BatchTypeID: {}, Current Quantity to Restore: {}", batchTypeId, currentQuantity);

                    if (currentQuantity <= 0) {
                        logger.warn("⚠️ Skipping restoration - Current quantity is {}", currentQuantity);
                        continue;
                    }

                    // Try find in BatchInventory (with box)
                    List<BatchInventory> batchInventories = batchInventoryRepository.findByParentBatchTypeId(batchTypeId);
                    if (!batchInventories.isEmpty()) {
                        BatchInventory targetInventory = batchInventories.stream()
                                .min((i1, i2) -> Integer.compare(i1.getBatch_quantity(), i2.getBatch_quantity()))
                                .orElse(batchInventories.get(0));

                        int beforeQty = targetInventory.getBatch_quantity();
                        targetInventory.setBatch_quantity(beforeQty + currentQuantity);
                        batchInventoryRepository.save(targetInventory);

                        logger.info("✅ Restored {} batches to BatchInventory (with box) ID: {}", currentQuantity, targetInventory.getInventoryid());
                        continue; // Skip searching in without box
                    }

                    // If not found, try BatchInventoryWithoutBox (without box)
                    List<BatchInventoryWithoutBox> batchInventoriesWithoutBox = batchInventoryWithoutBoxRepository.findByParentBatchTypeId(batchTypeId);
                    if (!batchInventoriesWithoutBox.isEmpty()) {
                        BatchInventoryWithoutBox targetInventory = batchInventoriesWithoutBox.stream()
                                .min((i1, i2) -> Integer.compare(i1.getBatch_quantity(), i2.getBatch_quantity()))
                                .orElse(batchInventoriesWithoutBox.get(0));

                        int beforeQty = targetInventory.getBatch_quantity();
                        targetInventory.setBatch_quantity(beforeQty + currentQuantity);
                        batchInventoryWithoutBoxRepository.save(targetInventory);

                        logger.info("✅ Restored {} batches to BatchInventoryWithoutBox ID: {}", currentQuantity, targetInventory.getInventoryid());
                        continue;
                    }

                    // If not found in either table
                    logger.error("❌ No BatchInventory or BatchInventoryWithoutBox found for batch type {} to restore {} batches", batchTypeId, currentQuantity);
                }


                // 5. Update linked orders status to "pending"
                List<DeliveryOrder> linkedOrders = deliveryOrderRepository.findByDelivery_Deliveryid(deliveryId);
                logger.info("Linked orders count: {}", linkedOrders.size());

                for (DeliveryOrder deliveryOrder : linkedOrders) {
                    CustomerOrder order = deliveryOrder.getCustomerOrder();
                    if (order != null && "ongoing".equalsIgnoreCase(order.getStatus())) {
                        order.setStatus("pending");
                        customerOrderRepository.save(order);
                        logger.info("✅ Updated order {} status to 'pending'", order.getOrderid());
                    }
                }
            }

            // Update delivery status
            delivery.setStatus(newStatus);
            deliveryRepository.save(delivery);

            logger.info("✅ Delivery {} status updated to {}", deliveryId, newStatus);
            if (newStatus == 0) {
                logger.info("✅ Trip completion process finished - resources restored and made available");
            }

            return true;

        } catch (Exception e) {
            logger.error("❌ Error updating delivery status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update delivery status: " + e.getMessage());
        }
    }

}
