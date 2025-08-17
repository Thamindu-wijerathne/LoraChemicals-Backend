package com.lorachemicals.Backend.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.CustomerOrderItemRequestDTO;
import com.lorachemicals.Backend.dto.CustomerOrderItemResponseDTO;
import com.lorachemicals.Backend.dto.CustomerOrderRequestDTO;
import com.lorachemicals.Backend.dto.CustomerOrderResponseDTO;
import com.lorachemicals.Backend.dto.TrendingProductsDTO;
import com.lorachemicals.Backend.model.BatchInventoryDelivery;
import com.lorachemicals.Backend.model.Customer;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.model.CustomerOrderItem;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.BatchInventoryDeliveryRepository;
import com.lorachemicals.Backend.repository.BatchInventoryRepository;
import com.lorachemicals.Backend.repository.CustomerOrderItemRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.CustomerRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import com.lorachemicals.Backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerOrderItemRepository customerOrderItemRepository;

    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BatchInventoryDeliveryRepository batchInventoryDeliveryRepository;


    @Autowired
    private BatchInventoryRepository batchInventoryRepository;

    @Transactional
    public CustomerOrder createOrder(CustomerOrderRequestDTO data) {
        try {
            // Validate and fetch user (customer)
            User user = userRepository.findById(data.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            // Create order object
            CustomerOrder order = new CustomerOrder();
            order.setUser(user);
            order.setStatus(data.getStatus());
            order.setTotal(data.getTotal());
            order.setDelivered_date(data.getDeliveredDate() != null ? data.getDeliveredDate() : new Date());

            // Save order to database first
            CustomerOrder savedOrder = orderRepository.save(order);

            // Save each order item
            for (CustomerOrderItemRequestDTO itemDTO : data.getOrderItems()) {
                CustomerOrderItem orderItem = new CustomerOrderItem();
                orderItem.setCustomerOrder(savedOrder);
                orderItem.setQuantity((long) itemDTO.getQuantity());

                // Get product info
                ProductTypeVolume ptv = productTypeVolumeRepository.findById(itemDTO.getPtvid())
                        .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found"));

                orderItem.setProductTypeVolume(ptv);

                // Calculate total price
                BigDecimal productTotal = ptv.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
                orderItem.setProductTotal(productTotal);

                // Save item
                customerOrderItemRepository.save(orderItem);
            }

            return savedOrder;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }
    }

    public List<CustomerOrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        List<CustomerOrder> orders = orderRepository.findByUser_Id(customerId);

        return orders.stream().map(order -> {
            CustomerOrderResponseDTO dto = new CustomerOrderResponseDTO();
            dto.setOrderid(order.getOrderid());
            dto.setDeliveredDate(order.getDelivered_date());
            dto.setStatus(order.getStatus());
            dto.setTotal(order.getTotal());
            dto.setCustomerId(order.getUser().getId());
            dto.setCustomerName(order.getUser().getName());

            // Map items (ptvid, quantity, productTotal only)
            List<CustomerOrderItemResponseDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
                CustomerOrderItemResponseDTO itemDTO = new CustomerOrderItemResponseDTO();

                ProductTypeVolume ptv = item.getProductTypeVolume();
                ProductType productType = ptv.getProductType();

                itemDTO.setPtvid(ptv.getPtvid());
                itemDTO.setName(ptv.getName());
                itemDTO.setQuantity(item.getQuantity().intValue());
                itemDTO.setUnitPrice(ptv.getUnitPrice());
                itemDTO.setProductTotal(item.getProductTotal());
                itemDTO.setImage(ptv.getImage());
                itemDTO.setProductTypeName(productType != null ? productType.getName() : null);
                itemDTO.setBottleTypeName(ptv.getBottletype() != null ? ptv.getBottletype().getName() : null);
                itemDTO.setLabelTypeName(ptv.getLabeltype() != null ? ptv.getLabeltype().getName() : null);
                itemDTO.setVolume(ptv.getVolume());


                return itemDTO;
            }).collect(Collectors.toList());


            dto.setItems(itemDTOs);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<CustomerOrderResponseDTO> getOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            CustomerOrderResponseDTO dto = new CustomerOrderResponseDTO();
            dto.setOrderid(order.getOrderid());
            dto.setDeliveredDate(order.getDelivered_date());
            dto.setStatus(order.getStatus());
            dto.setTotal(order.getTotal());
            dto.setCustomerId(order.getUser().getId());
            dto.setCustomerName(order.getUser().getName());

            Customer customer = customerRepository.findByUserId(order.getUser().getId());

            if (customer != null && customer.getRoute() != null) {
                dto.setRoute(customer.getRoute().getDistrict()); // assuming 'route' in DTO is a String
                dto.setRouteid(customer.getRoute().getRouteid());
            } else {
                dto.setRoute("N/A"); // or null, as fallback
            }


            // Map items (ptvid, quantity, productTotal only)
            List<CustomerOrderItemResponseDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
                CustomerOrderItemResponseDTO itemDTO = new CustomerOrderItemResponseDTO();

                ProductTypeVolume ptv = item.getProductTypeVolume();
                ProductType productType = ptv.getProductType();

                itemDTO.setPtvid(ptv.getPtvid());
                itemDTO.setName(ptv.getName());
                itemDTO.setQuantity(item.getQuantity().intValue());
                itemDTO.setUnitPrice(ptv.getUnitPrice());
                itemDTO.setProductTotal(item.getProductTotal());
                itemDTO.setImage(ptv.getImage());
                itemDTO.setProductTypeName(productType != null ? productType.getName() : null);
                itemDTO.setBottleTypeName(ptv.getBottletype() != null ? ptv.getBottletype().getName() : null);
                itemDTO.setLabelTypeName(ptv.getLabeltype() != null ? ptv.getLabeltype().getName() : null);
                itemDTO.setVolume(ptv.getVolume());

                return itemDTO;
            }).collect(Collectors.toList());

            dto.setItems(itemDTOs);
            return dto;
        }).collect(Collectors.toList());
    }

    public void acceptOrder(Long id) {
        CustomerOrder order = orderRepository.findByOrderid(id);
        if (order == null) {
            throw new RuntimeException("Order not found with id " + id);
        }
        order.setStatus("accepted");  // Update status to accepted
        orderRepository.save(order);
    }

    public List<TrendingProductsDTO> getTrendingProducts() {
        return customerOrderItemRepository.findTrendingProducts(PageRequest.of(0, 5)); // top 5
    }


    @Transactional
    public void completeOrder(Long orderId, CustomerOrderRequestDTO requestDTO) {
        CustomerOrder order = orderRepository.findByOrderid(orderId);
        if (order == null) throw new RuntimeException("Order not found: " + orderId);

        // Validate inventory before making any changes
        for (CustomerOrderRequestDTO.BatchDeduction deduction : requestDTO.getBatchDeductions()) {
            BatchInventoryDelivery batch = batchInventoryDeliveryRepository
                    .findByBatchType_IdAndDelivery_DeliveryidAndId_TypeAndId_Datetime(
                            deduction.getBatchtypeid(),
                            deduction.getDeliveryid(),
                            deduction.getType(),
                            deduction.getDatetime()
                    );

            if (batch == null)
                throw new RuntimeException("Batch not found for batch type ID: " + deduction.getBatchtypeid());
            
            if (batch.getCurrentQuantity() < deduction.getBoxesToDeduct()) {
                throw new RuntimeException("Insufficient inventory in batch " + deduction.getBatchtypeid() + 
                    ". Available: " + batch.getCurrentQuantity() + ", Requested: " + deduction.getBoxesToDeduct());
            }
        }

        // If validation passes, update order status
        order.setStatus("Complete");
        orderRepository.save(order);

        // Deduct batches
        for (CustomerOrderRequestDTO.BatchDeduction deduction : requestDTO.getBatchDeductions()) {
            BatchInventoryDelivery batch = batchInventoryDeliveryRepository
                    .findByBatchType_IdAndDelivery_DeliveryidAndId_TypeAndId_Datetime(
                            deduction.getBatchtypeid(),
                            deduction.getDeliveryid(),
                            deduction.getType(),
                            deduction.getDatetime()
                    );

            // Update current quantity
            int newQuantity = batch.getCurrentQuantity() - deduction.getBoxesToDeduct();
            batch.setCurrentQuantity(newQuantity);
            batchInventoryDeliveryRepository.save(batch);
            
            System.out.println("✅ Deducted " + deduction.getBoxesToDeduct() + 
                " boxes from batch " + deduction.getBatchtypeid() + 
                ". New quantity: " + newQuantity);
        }

        System.out.println("🎉 Order " + orderId + " completed successfully with " + 
            requestDTO.getBatchDeductions().size() + " batch deductions");
    }

}
