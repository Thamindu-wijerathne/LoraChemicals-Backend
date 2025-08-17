package com.lorachemicals.Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.BillItemRequestDTO;
import com.lorachemicals.Backend.dto.BillItemResponseDTO;
import com.lorachemicals.Backend.dto.BillRequestDTO;
import com.lorachemicals.Backend.dto.BillResponseDTO;
import com.lorachemicals.Backend.model.Bill;
import com.lorachemicals.Backend.model.BillItem;
import com.lorachemicals.Backend.model.CustomerBill;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.repository.BillItemRepository;
import com.lorachemicals.Backend.repository.BillRepository;
import com.lorachemicals.Backend.repository.CustomerBillRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import com.lorachemicals.Backend.repository.SalesRepRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private CustomerBillRepository customerBillRepository;

    @Autowired
    private CustomerBillService customerBillService;

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

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public BillResponseDTO getBillById(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));
        return convertToDTO(bill);
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
        
        // Add customer information if available using CustomerBillService
        CustomerBill customerBill = customerBillService.getCustomerBillByBillId(bill.getBillid());
        if (customerBill != null) {
            dto.setShopName(customerBill.getShop_name());
            dto.setAddress(customerBill.getAddress());
            dto.setPhone(customerBill.getPhone());
            dto.setDistrict(customerBill.getDistrict());
        }
        
        return dto;
    }

}
