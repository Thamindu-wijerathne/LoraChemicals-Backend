package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BillRequestDTO;
import com.lorachemicals.Backend.model.Bill;
import com.lorachemicals.Backend.model.CustomerBill;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.repository.BillRepository;
import com.lorachemicals.Backend.repository.CustomerBillRepository;
import com.lorachemicals.Backend.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private CustomerBillRepository customerBillRepository;

    public Bill createBill(Long srepId, BillRequestDTO data) {
        SalesRep salesRep = salesRepRepository.findById(srepId)
                .orElseThrow(() -> new RuntimeException("SalesRep not found"));

        Bill bill = new Bill();
        bill.setDatetime(data.getDatetime());
        bill.setTotal(data.getTotal());
        bill.setSalesRep(salesRep);
        bill = billRepository.save(bill); // persist bill first

        CustomerBill customerBill = new CustomerBill();
        customerBill.setShop_name(data.getShop_name());
        customerBill.setAddress(data.getAddress());
        customerBill.setPhone(data.getPhone());
        customerBill.setBill(bill);

        customerBillRepository.save(customerBill);

        return bill;
    }

}
