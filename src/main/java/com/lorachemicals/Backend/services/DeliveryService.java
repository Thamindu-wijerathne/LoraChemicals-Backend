package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.CreateDeliveryRequestDTO;
import com.lorachemicals.Backend.dto.DeliveryInventoryRequestDTO;
import com.lorachemicals.Backend.model.BatchInventory;
import com.lorachemicals.Backend.model.Delivery;
import com.lorachemicals.Backend.repository.BatchInventoryRepository;
import com.lorachemicals.Backend.repository.DeliveryInventoryRepository;
import com.lorachemicals.Backend.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private BatchInventoryRepository batchInventoryRepository;

    @Autowired
    private DeliveryInventoryRepository deliveryInventoryRepository;

    //get all
    public List<Delivery> getAllDeliveries(){
        try{
            return deliveryRepository.findAll();
        }
        catch(Exception e){
            throw new RuntimeException("Failed to get deliveries: " + e.getMessage(), e);
        }
    }

    //get by id
    public Optional<Delivery> getDeliveryById(Long id){
        try{
            return deliveryRepository.findById(id);

        }
        catch(Exception e){
            throw new RuntimeException("Failed to get deliveries: " + e.getMessage(), e);
        }
    }

    //create
    @Transactional
//    public void createDeliveryWithItems(CreateDeliveryRequestDTO request) {
//        Delivery delivery = new Delivery();
//        delivery.setDeliveryid(request.getDeliveryId());
//        delivery.setDeliverydate(request.getDeliveryDate());
//        deliveryRepository.save(delivery);
//
//        for (DeliveryInventoryRequestDTO itemRequest : request.getItems()) {
//            int remainingQty = itemRequest.getQuantity();
//            List<BatchInventory> batches = batchInventoryRepository
//                    .findByInventoryidOrderByExpiredateAsc(itemRequest.getInventoryId());
//
//            for (BatchInventory batch : batches) {
//                if (remainingQty <= 0) break;
//
//                int available = batch.getQuantity();
//                if (available <= 0) continue;
//
//                int deduct = Math.min(available, remainingQty);
//
//                // Update batch
//                batch.setQuantity(available - deduct);
//                batchInventoryRepository.save(batch);
//
//                // Save DeliveryInventory
//                DeliveryInventoryId id = new DeliveryInventoryId(
//                        request.getDeliveryId(),
//                        itemRequest.getInventoryId(),
//                        request.getDeliveryDate()
//                );
//
//                DeliveryInventory deliveryInventory = new DeliveryInventory();
//                deliveryInventory.setId(id);
//                deliveryInventory.setQuantity(deduct);
//                deliveryInventoryRepository.save(deliveryInventory);
//
//                remainingQty -= deduct;
//            }
//
//            if (remainingQty > 0) {
//                throw new RuntimeException("Not enough stock for inventoryId: " + itemRequest.getInventoryId());
//            }
//        }
//    }



    //update

    //delete
    public void deleteDeliveryById(Long id){
        try{
            deliveryRepository.deleteById(id);
        }
        catch(Exception e){
            throw new RuntimeException("Failed to delete deliveries: " + e.getMessage(), e);
        }
    }
}
