package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BillItemRequestDTO;
import com.lorachemicals.Backend.dto.BillRequestDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private CustomerBillRepository customerBillRepository;

    @Autowired
    private ProductTypeVolumeRepository productTypeVolumeRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    public Bill createBill(Long srepId, BillRequestDTO data) {
        SalesRep salesRep = salesRepRepository.findById(srepId)
                .orElseThrow(() -> new RuntimeException("SalesRep not found"));

        // Save Bill
        Bill bill = new Bill();
        bill.setDatetime(data.getDatetime());
        bill.setTotal(data.getTotal());
        bill.setSalesRep(salesRep);
        bill = billRepository.save(bill);

        // Save CustomerBill
        CustomerBill customerBill = new CustomerBill();
        customerBill.setShop_name(data.getShop_name());
        customerBill.setAddress(data.getAddress());
        customerBill.setPhone(data.getPhone());
        customerBill.setBill(bill);
        customerBill = customerBillRepository.save(customerBill);

        // Save BillItems
        if (data.getItems() != null) {
            for (BillItemRequestDTO itemDto : data.getItems()) {
                ProductTypeVolume ptv = productTypeVolumeRepository.findById(itemDto.getPtvid())
                        .orElseThrow(() -> new RuntimeException("ProductTypeVolume not found for ID: " + itemDto.getPtvid()));

                BillItem item = new BillItem();
                item.setProductTypeVolume(ptv);
                item.setQuantity((long) itemDto.getQuantity());
                item.setTotal(itemDto.getTotal());
                item.setCustomerBill(customerBill);

                billItemRepository.save(item);
            }
        }

        return bill;
    }

    public List<Bill> getSalesrepBill(Long srepId) {
        return billRepository.findBySalesRep_Srepid(srepId);
    }

}
