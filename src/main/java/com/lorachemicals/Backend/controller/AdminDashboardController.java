package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.AdminDashboardDTO;
import com.lorachemicals.Backend.services.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin-dashboard")

public class AdminDashboardController {
    @Autowired
    private AdminDashboardService dashboardService;

    @GetMapping("/total-orders")
    public AdminDashboardDTO getTotalOrders(){
        return dashboardService.getTotalOrders();
    }

    @GetMapping("/total-sales")
    public double getTotalSales(){
        return dashboardService.getTotalSales();
    }
}
