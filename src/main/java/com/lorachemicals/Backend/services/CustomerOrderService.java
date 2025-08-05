package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.*;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.CustomerOrderItemRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import com.lorachemicals.Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerOrderItemRepository customerOrderItemRepository;

    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;

    @Autowired
    private UserRepository userRepository;

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
                itemDTO.setQuantity(item.getQuantity().intValue());
                itemDTO.setProductTotal(item.getProductTotal());

                itemDTO.setImage(ptv.getImage());
                itemDTO.setProductTypeName(productType != null ? productType.getName() : null);

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

            // Map items (ptvid, quantity, productTotal only)
            List<CustomerOrderItemResponseDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
                CustomerOrderItemResponseDTO itemDTO = new CustomerOrderItemResponseDTO();

                ProductTypeVolume ptv = item.getProductTypeVolume();
                ProductType productType = ptv.getProductType();

                itemDTO.setPtvid(ptv.getPtvid());
                itemDTO.setQuantity(item.getQuantity().intValue());
                itemDTO.setProductTotal(item.getProductTotal());

                itemDTO.setImage(ptv.getImage());
                itemDTO.setProductTypeName(productType != null ? productType.getName() : null);

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



}
