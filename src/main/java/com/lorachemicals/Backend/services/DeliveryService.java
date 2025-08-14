package com.lorachemicals.Backend.services;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BatchInventoryDeliveryResponseDTO;
import com.lorachemicals.Backend.dto.DeductExtrasDTO;
import com.lorachemicals.Backend.dto.DeductExtrasRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryResponseDTO;
import com.lorachemicals.Backend.dto.SalesRepDeliveryResponseDTO;
import com.lorachemicals.Backend.model.BatchInventory;
import com.lorachemicals.Backend.model.BatchInventoryDelivery;
import com.lorachemicals.Backend.model.BatchInventoryDeliveryId;
import com.lorachemicals.Backend.model.BatchInventoryWithoutBox;
import com.lorachemicals.Backend.model.BatchType;
import com.lorachemicals.Backend.model.BatchTypeWithoutBox;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.model.Delivery;
import com.lorachemicals.Backend.model.DeliveryOrder;
import com.lorachemicals.Backend.model.DeliveryOrderId;
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
import com.lorachemicals.Backend.repository.CustomerOrderItemRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.DeliveryOrderRepository;
import com.lorachemicals.Backend.repository.DeliveryRepository;
import com.lorachemicals.Backend.repository.RouteRepository;
import com.lorachemicals.Backend.repository.SalesRepRepository;
import com.lorachemicals.Backend.repository.VehicleRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;

import jakarta.transaction.Transactional;
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

    @Autowired
    private CustomerOrderItemRepository customerOrderItemRepository;

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


    //view details about on going details by srep
    public SalesRepDeliveryResponseDTO getDetailedDeliveryBySalesRep(Long srepid) {
        try {
            Delivery delivery = deliveryRepository
                    .findBySalesRep_SrepidAndStatus(srepid, 1)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No active deliveries found for sales rep " + srepid));

            // Batch inventories
            List<SalesRepDeliveryResponseDTO.DeliveryBatchInventoryDetail> batchDetails =
                    batchInventoryDeliveryRepository.findByDelivery_Deliveryid(delivery.getDeliveryid())
                            .stream()
                            .map(b -> {
                                SalesRepDeliveryResponseDTO.DeliveryBatchInventoryDetail d = new SalesRepDeliveryResponseDTO.DeliveryBatchInventoryDetail();
                                if (b.getId() != null) {
                                    d.setBatchtypeid(b.getId().getBatchtypeid());
                                    d.setDeliveryid(b.getId().getDeliveryid());
                                    d.setDatetime(b.getId().getDatetime());
                                    d.setType(b.getId().getType());
                                }
                                d.setQuantity(b.getQuantity());
                                d.setCurrentQuantity(b.getCurrentQuantity());

                                Optional.ofNullable(b.getWarehouseManager())
                                        .ifPresent(wm -> {
                                            d.setWmid(wm.getWmid());
                                            Optional.ofNullable(wm.getUser())
                                                    .ifPresent(u -> d.setWarehouseManagerName((u.getFname() != null ? u.getFname() : "") + " " + (u.getLname() != null ? u.getLname() : "")));
                                        });

                                Optional.ofNullable(b.getBatchType()).ifPresent(parent -> {
                                    d.setBatchTypeName(parent.getBatchtypename());
                                    
                                    // Set the parent batch type's unique batch code
                                    d.setBatchCode(parent.getUniqueBatchCode());
                                    
                                    logger.info("🏷️ Batch Type ID: {}, Name: {}, Unique Code: {}", 
                                        parent.getId(), parent.getBatchtypename(), parent.getUniqueBatchCode());
                                    
                                    batchTypeRepository.findById(parent.getId()).ifPresentOrElse(bt -> {
                                        d.setInventoryType("with_box");
                                        Optional.ofNullable(bt.getProductTypeVolume()).ifPresent(ptv -> {
                                            d.setPtvid(ptv.getPtvid().intValue()); // Set the ptvid (convert Long to int)
                                            d.setImageurl(ptv.getImage()); // Set the image URL
                                            Optional.ofNullable(ptv.getProductType()).ifPresent(pt -> d.setProductName(pt.getName()));
                                            Optional.ofNullable(ptv.getVolume()).ifPresent(v -> d.setVolume(v.doubleValue()));
                                        });
                                        Optional.ofNullable(bt.getBoxType()).ifPresent(box -> {
                                            d.setBoxTypeName(box.getName());
                                            d.setQuantityInBox(box.getQuantityInBox());
                                        });
                                    }, () -> batchTypeWithoutBoxRepository.findById(parent.getId()).ifPresent(btwb -> {
                                        d.setInventoryType("without_box");
                                        Optional.ofNullable(btwb.getProductTypeVolume()).ifPresent(ptv -> {
                                            d.setPtvid(ptv.getPtvid().intValue()); // Set the ptvid (convert Long to int)
                                            d.setImageurl(ptv.getImage()); // Set the image URL
                                            Optional.ofNullable(ptv.getProductType()).ifPresent(pt -> d.setProductName(pt.getName()));
                                            Optional.ofNullable(ptv.getVolume()).ifPresent(v -> d.setVolume(v.doubleValue()));
                                        });
                                        d.setQuantityInBox(1);
                                    }));
                                });
                                return d;
                            }).collect(Collectors.toList());

            // Orders
            List<SalesRepDeliveryResponseDTO.DeliveryOrderDetail> orderDetails =
                    deliveryOrderRepository.findByDelivery_Deliveryid(delivery.getDeliveryid())
                            .stream()
                            .map(doe -> {
                                SalesRepDeliveryResponseDTO.DeliveryOrderDetail od = new SalesRepDeliveryResponseDTO.DeliveryOrderDetail();
                                Optional.ofNullable(doe.getCustomerOrder()).ifPresent(order -> {
                                    od.setOrderid(order.getOrderid());
                                    od.setOrderStatus(order.getStatus());
                                    Optional.ofNullable(order.getTotal()).ifPresent(t -> od.setOrderTotal(t.doubleValue()));
                                    Optional.ofNullable(doe.getDelivery()).ifPresent(d -> od.setDeliveryid(d.getDeliveryid()));

                                    Optional.ofNullable(order.getUser()).ifPresent(c -> {
                                        od.setCustomerId(c.getId());
                                        od.setCustomerName((c.getFname() != null ? c.getFname() : "") + " " + (c.getLname() != null ? c.getLname() : ""));
                                        od.setCustomerEmail(c.getEmail());
                                        od.setCustomerAddress(c.getAddress());
                                        od.setCustomerPhone(c.getPhone());
                                    });

                                    Optional.ofNullable(order.getDelivered_date())
                                            .ifPresent(dd -> od.setOrderDate(dd.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));

                                    od.setOrderItems(
                                            customerOrderItemRepository.findByCustomerOrder_Orderid(order.getOrderid())
                                                    .stream()
                                                    .map(item -> {
                                                        SalesRepDeliveryResponseDTO.OrderItem oi = new SalesRepDeliveryResponseDTO.OrderItem();
                                                        oi.setItemid(item.getOrderitemid());
                                                        Optional.ofNullable(item.getQuantity()).ifPresent(q -> {
                                                            oi.setQuantity(q.intValue());
                                                            Optional.ofNullable(item.getProductTotal())
                                                                    .ifPresent(total -> {
                                                                        if (q > 0) {
                                                                            oi.setUnitPrice(total.divide(BigDecimal.valueOf(q)).doubleValue());
                                                                            oi.setTotalPrice(total.doubleValue());
                                                                        }
                                                                    });
                                                        });
                                                        Optional.ofNullable(item.getProductTypeVolume()).ifPresent(ptv -> {
                                                            Optional.ofNullable(ptv.getProductType()).ifPresent(pt -> oi.setProductTypeName(pt.getName()));
                                                            Optional.ofNullable(ptv.getVolume()).ifPresent(v -> oi.setVolume(v.doubleValue()));
                                                            oi.setProductImage(ptv.getImage());
                                                        });
                                                        return oi;
                                                    })
                                                    .collect(Collectors.toList())
                                    );
                                });
                                return od;
                            }).collect(Collectors.toList());

            // Build main DTO
            SalesRepDeliveryResponseDTO dto = new SalesRepDeliveryResponseDTO();
            dto.setDeliveryid(delivery.getDeliveryid());
            dto.setDispatchdate(delivery.getDispatchdate());
            dto.setCamedate(delivery.getCamedate());
            dto.setStatus(delivery.getStatus());

            Optional.ofNullable(delivery.getSalesRep()).ifPresent(sr -> {
                dto.setSrepid(sr.getSrepid());
                Optional.ofNullable(sr.getUser()).ifPresent(u -> {
                    dto.setSalesRepName((u.getFname() != null ? u.getFname() : "") + " " + (u.getLname() != null ? u.getLname() : ""));
                    dto.setSalesRepEmail(u.getEmail());
                });
            });

            Optional.ofNullable(delivery.getRoute()).ifPresent(r -> {
                dto.setRouteid(r.getRouteid());
                dto.setRouteName(r.getDistrict() != null ? r.getDistrict() : "Unknown Route");
            });

            Optional.ofNullable(delivery.getVehicle()).ifPresent(v -> {
                dto.setVehicleid(v.getId());
                dto.setVehicleNumber(v.getVehicleNo() != null ? v.getVehicleNo() : "Unknown Vehicle");
                dto.setVehicleLicensePlate(v.getVehicleNo() != null ? v.getVehicleNo() : "Unknown");
            });

            dto.setBatchInventoryDetails(batchDetails);
            dto.setDeliveryOrders(orderDetails);

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch delivery details for sales rep " + srepid, e);
        }
    }

    @Transactional
    public String deductExtrasFromVehicle(DeductExtrasRequestDTO request) {
        try {
            logger.info("Processing extras deduction for sales rep: {}", request.getSrepid());
            
            if (request.getDeductions() == null || request.getDeductions().isEmpty()) {
                throw new RuntimeException("No deductions provided in request");
            }

            for (DeductExtrasDTO deduction : request.getDeductions()) {
                logger.info("Processing deduction for batch: {}", deduction.getBatchtypeid());
                
                BatchInventoryDelivery batch = batchInventoryDeliveryRepository
                        .findByBatchType_IdAndDelivery_DeliveryidAndId_TypeAndId_Datetime(
                                deduction.getBatchtypeid(),
                                deduction.getDeliveryid(),
                                deduction.getType(),
                                deduction.getDatetime()
                        );

                if (batch == null) {
                    throw new RuntimeException("Batch not found: " + deduction.getBatchtypeid());
                }

                // Check if there's enough quantity to deduct
                if (batch.getCurrentQuantity() < deduction.getBoxesToDeduct()) {
                    throw new RuntimeException("Insufficient quantity in batch " + deduction.getBatchtypeid() + 
                            ". Available: " + batch.getCurrentQuantity() + ", Requested: " + deduction.getBoxesToDeduct());
                }

                // Deduct the quantity
                batch.setCurrentQuantity(batch.getCurrentQuantity() - deduction.getBoxesToDeduct());
                batchInventoryDeliveryRepository.save(batch);
                
                logger.info("Successfully deducted {} boxes from batch {}", deduction.getBoxesToDeduct(), deduction.getBatchtypeid());
            }

            return "Successfully processed " + request.getDeductions().size() + " deductions for sales rep " + request.getSrepid();

        } catch (Exception e) {
            logger.error("Error processing extras deduction: ", e);
            throw new RuntimeException("Failed to deduct extras from vehicle inventory: " + e.getMessage(), e);
        }
    }
}
