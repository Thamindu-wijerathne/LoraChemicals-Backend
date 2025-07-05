package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.CustomerOrderItemRequestDTO;
import com.lorachemicals.Backend.dto.CustomerOrderRequestDTO;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.model.CustomerOrderItem;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.CustomerOrderItemRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import com.lorachemicals.Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerOrderItemRepository customerOrderItemRepository;  // Add this

    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;  // Add this

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

            // Now save each order item linked to this order
            for (CustomerOrderItemRequestDTO itemDTO : data.getOrderItems()) {
                CustomerOrderItem orderItem = new CustomerOrderItem();
                orderItem.setCustomerOrder(savedOrder);
                orderItem.setQuantity((long) itemDTO.getQuantity());

                // Fetch the ProductTypeVolume entity
                ProductTypeVolume ptv = productTypeVolumeRepository.findById(itemDTO.getPtvid())
                        .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found"));

                orderItem.setProductTypeVolume(ptv);

                // Calculate product total price for this order item
                BigDecimal productTotal = ptv.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
                orderItem.setProductTotal(productTotal);

                // Save order item
                customerOrderItemRepository.save(orderItem);
            }

            return savedOrder;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }
    }
}
