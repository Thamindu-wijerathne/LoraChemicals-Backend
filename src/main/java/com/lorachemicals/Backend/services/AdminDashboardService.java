package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.AdminDashboardDTO;
import com.lorachemicals.Backend.dto.DistrictSalesDTO;
import com.lorachemicals.Backend.repository.CustomerOrderItemRepository;
import com.lorachemicals.Backend.repository.CustomerOrderRepository;
import com.lorachemicals.Backend.repository.CustomerRepository;
import com.lorachemicals.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private CustomerRepository customerRepository;

    public long getActiveDistricts() {
        return customerRepository.countDistinctRouteIds();
    }

    @Autowired
    private UserRepository userRepository;

    public long getActiveStaffCount() {
        return userRepository.countActiveStaff();
    }

    public List<DistrictSalesDTO> getTotalSalesByDistrict() {
        return orderRepository.getTotalSalesPerDistrict();
    }


}
