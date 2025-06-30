package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.InventoryRequestDTO;
import com.lorachemicals.Backend.dto.InventoryResponseDTO;
import com.lorachemicals.Backend.model.Inventory;
import com.lorachemicals.Backend.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository invrepo;

    //All inventories
    public List<InventoryResponseDTO> getall() {
        try {
            return invrepo.findAll()
                    .stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve vehicles: " + e.getMessage());
        }
    }

    //delete a inventory
    public void deleteInventory(Long id) {
        try {
            if (!invrepo.existsById(id)) {
                throw new RuntimeException("Vehicle not found with id: " + id);
            }
            invrepo.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    //get by id
    public InventoryResponseDTO getbyid(Long id) {
        return invrepo.findById(id)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    // Create inventory
    public InventoryResponseDTO createInventory(InventoryRequestDTO reqDTO) {
        try {
            Inventory newInv = new Inventory();
            newInv.setLocation(reqDTO.getLocation());

            Inventory savedInv = invrepo.save(newInv);
            return convertToResponseDTO(savedInv);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create inventory: " + e.getMessage());
        }
    }

    //update
    public InventoryResponseDTO updateinv(Long id, InventoryRequestDTO newreq){
        try{
            Inventory inventory = invrepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invemtory not found with id: " + id));

            inventory.setLocation(newreq.getLocation());

            Inventory updatedinv = invrepo.save(inventory);

            return convertToResponseDTO(updatedinv);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update vehicle: " + e.getMessage());
        }
    }



    //converter
    private InventoryResponseDTO convertToResponseDTO(Inventory inv){
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setID(inv.getId());
        dto.setLocation(inv.getLocation());
        return dto;
    }


}
