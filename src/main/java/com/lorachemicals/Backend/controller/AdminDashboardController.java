package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.AdminDashboardDTO;
import com.lorachemicals.Backend.dto.DistrictSalesDTO;
import com.lorachemicals.Backend.dto.SalesEmployeeDTO;
import com.lorachemicals.Backend.services.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/active-districs")
    public long getActiveDistricts() {
        return dashboardService.getActiveDistricts();
    }

    @GetMapping("/active-staff")
    public long getActiveStaffCount() {
        return dashboardService.getActiveStaffCount();
    }

    @GetMapping("/sales-by-district")
    public ResponseEntity<List<DistrictSalesDTO>> getSalesByDistrict() {
        return ResponseEntity.ok(dashboardService.getTotalSalesByDistrict());
    }

    @GetMapping("/sales-orders-by-employee")
    public ResponseEntity<List<SalesEmployeeDTO>> getSalesAndOrdersByEmployee(Pageable pageable) {
        return ResponseEntity.ok(dashboardService.getSalesAndOrdersByEmployee(pageable));
    }


}
