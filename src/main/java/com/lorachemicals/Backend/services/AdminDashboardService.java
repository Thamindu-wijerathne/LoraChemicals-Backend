package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.AdminDashboardDTO;
import com.lorachemicals.Backend.repository.CustomerOrderItemRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {
    @Autowired
    private CustomerOrderRepository orderRepository;


    public AdminDashboardDTO getTotalOrders(){
        long count = orderRepository.count();
        return new AdminDashboardDTO((int) count);
    }

    @Autowired
    private CustomerOrderItemRepository orderItemRepository;

    public double getTotalSales(){
        return orderItemRepository.getTotalSales();
    }

}
