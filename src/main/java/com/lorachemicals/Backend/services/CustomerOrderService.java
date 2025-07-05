package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.CustomerOrderRequestDTO;
import com.lorachemicals.Backend.model.CustomerOrder;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerOrderService {

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

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

            // Save to database
            return orderRepository.save(order);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }
    }
}
