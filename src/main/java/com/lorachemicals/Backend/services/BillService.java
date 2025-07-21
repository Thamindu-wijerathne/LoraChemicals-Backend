package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.BillItemRequestDTO;
import com.lorachemicals.Backend.dto.BillItemResponseDTO;
import com.lorachemicals.Backend.dto.BillRequestDTO;
import com.lorachemicals.Backend.dto.BillResponseDTO;
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
        customerBill.setDistrict(data.getDistrict());
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

                item.setBill(bill);

                billItemRepository.save(item);
            }
        }

        return bill;
    }

    public List<Bill> getSalesrepBill(Long srepId) {
        return billRepository.findBySalesRep_Srepid(srepId);
    }

    public BillResponseDTO convertToDTO(Bill bill) {
        BillResponseDTO dto = new BillResponseDTO();
        dto.setBillid(bill.getBillid());
        dto.setTotal(bill.getTotal());
        dto.setDatetime(bill.getDatetime());
        dto.setSalesRepId(bill.getSalesRep().getSrepid());
        dto.setSalesRepName(bill.getSalesRep().getUser().getFname());

        List<BillItemResponseDTO> itemDTOs = bill.getBillItems().stream().map(item -> {
            BillItemResponseDTO itemDTO = new BillItemResponseDTO();
            ProductTypeVolume ptv = item.getProductTypeVolume();
            itemDTO.setBillitemid(item.getBillitemid());
            itemDTO.setTotal(item.getTotal());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPtvid(ptv.getPtvid());
            itemDTO.setPtvName(ptv.getName());
            itemDTO.setVolume(ptv.getVolume());
            itemDTO.setUnitPrice(ptv.getUnitPrice());
            itemDTO.setImage(ptv.getImage());
            return itemDTO;
        }).toList();


        dto.setBillItems(itemDTOs);
        return dto;
    }

}
