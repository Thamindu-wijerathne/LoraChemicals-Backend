package com.lorachemicals.Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.model.CustomerBill;
import com.lorachemicals.Backend.repository.CustomerBillRepository;

@Service
public class CustomerBillService {
    
    @Autowired
    private CustomerBillRepository customerBillRepository;
    
    public CustomerBill getCustomerBillByBillId(Long billId) {
        return customerBillRepository.findByBill_Billid(billId);
    }
}
