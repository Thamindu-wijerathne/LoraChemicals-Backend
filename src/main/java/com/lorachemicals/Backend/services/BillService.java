package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BillRequestDTO;
import com.lorachemicals.Backend.model.Bill;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.repository.BillRepository;
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

    public Bill createBill(Long salesRepId, BillRequestDTO dto) {
        SalesRep salesRep = salesRepRepository.findById(salesRepId)
                .orElseThrow(() -> new IllegalArgumentException("SalesRep not found"));

        Bill bill = new Bill();
        bill.setTotal(dto.getTotal());
        bill.setDatetime(dto.getDatetime() != null ? dto.getDatetime() : new Date());
        bill.setSalesRep(salesRep);

        return billRepository.save(bill);
    }
}
