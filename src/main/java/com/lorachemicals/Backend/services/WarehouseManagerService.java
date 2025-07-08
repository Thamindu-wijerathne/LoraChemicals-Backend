package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseManagerService {
    private final WarehouseManagerRepository warehouseManagerRepo;

    public WarehouseManagerService(WarehouseManagerRepository warehouseManagerRepo) {
        this.warehouseManagerRepo = warehouseManagerRepo;
    }

    public void saveWarehouseManager(WarehouseManager warehouseManager) {
        warehouseManagerRepo.save(warehouseManager);
    }

    public void deleteByUserId(Long userId) {
        WarehouseManager wm = warehouseManagerRepo.findByUserId(userId);
        if (wm != null) {
            warehouseManagerRepo.delete(wm);
        }
    }
    public WarehouseManager getWarehouseManagerById(Long Id) {
        return warehouseManagerRepo.findByUserId(Id);
    }


}
