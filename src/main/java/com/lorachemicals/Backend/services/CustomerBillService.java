package com.lorachemicals.Backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.CustomerBillResponseDTO;
import com.lorachemicals.Backend.model.CustomerBill;
import com.lorachemicals.Backend.repository.CustomerBillRepository;

@Service
public class CustomerBillService {
    
    @Autowired
    private CustomerBillRepository customerBillRepository;
    
    public CustomerBill getCustomerBillByBillId(Long billId) {
        return customerBillRepository.findByBill_Billid(billId);
    }
    
    public List<CustomerBill> getCustomerBillsByDeliveryId(Long deliveryId) {
        return customerBillRepository.findByDelivery_Deliveryid(deliveryId);
    }
    
    public List<CustomerBill> getCustomerBillsBySalesRepId(Long srepId) {
        return customerBillRepository.findByBill_SalesRep_Srepid(srepId);
    }
    
    public CustomerBill saveCustomerBill(CustomerBill customerBill) {
        return customerBillRepository.save(customerBill);
    }
    
    /**
     * Convert CustomerBill entity to CustomerBillResponseDTO
     */
    public CustomerBillResponseDTO convertToResponseDTO(CustomerBill customerBill) {
        CustomerBillResponseDTO dto = new CustomerBillResponseDTO();
        
        dto.setCbillid(customerBill.getCbillid());
        dto.setShop_name(customerBill.getShop_name());
        dto.setAddress(customerBill.getAddress());
        dto.setPhone(customerBill.getPhone());
        dto.setDistrict(customerBill.getDistrict());
        
        // Bill information
        if (customerBill.getBill() != null) {
            dto.setBillid(customerBill.getBill().getBillid());
            dto.setTotal(customerBill.getBill().getTotal());
            dto.setDatetime(customerBill.getBill().getDatetime().toString());
            
            // Sales rep information
            if (customerBill.getBill().getSalesRep() != null) {
                dto.setSalesRepId(customerBill.getBill().getSalesRep().getSrepid());
                if (customerBill.getBill().getSalesRep().getUser() != null) {
                    String fullName = (customerBill.getBill().getSalesRep().getUser().getFname() != null ? 
                        customerBill.getBill().getSalesRep().getUser().getFname() : "") + " " + 
                        (customerBill.getBill().getSalesRep().getUser().getLname() != null ? 
                        customerBill.getBill().getSalesRep().getUser().getLname() : "");
                    dto.setSalesRepName(fullName.trim());
                }
            }
        }
        
        // Delivery information
        if (customerBill.getDelivery() != null) {
            dto.setDeliveryid(customerBill.getDelivery().getDeliveryid());
        }
        
        return dto;
    }
}
