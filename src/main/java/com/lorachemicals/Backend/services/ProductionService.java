package com.lorachemicals.Backend.services;


import com.lorachemicals.Backend.dto.ProductionDetailedResponseDTO;
import com.lorachemicals.Backend.dto.ProductionRequestDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductionService {

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private WarehouseManagerRepository warehouseManagerRepository;

    @Autowired
    private MixerRepository mixerRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeItemRepository recipeItemRepository;

    @Autowired
    private SupplierRawMaterialRepository supplierRawMaterialRepository;

    @Autowired
    private RawChemicalRepository rawChemicalRepository;

    //get all
    public List<Production> findAll() {
        try{
            return productionRepository.findAll();
        }catch(Exception e){
            throw new RuntimeException("Failed to find all productions: " + e.getMessage(), e);
        }
    }

    //get the production that has the latest exp.date
    public ProductionDetailedResponseDTO getSoonestExpireProduction() {
        try {
            // Find earliest production with status confirmed
            Production production = productionRepository.findTopByStatusIgnoreCaseOrderByExpiredateAsc("confirmed")
                    .orElseThrow(() -> new RuntimeException("No confirmed production found"));

            return convertToDetailedDTO(production);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get soonest expire production: " + e.getMessage(), e);
        }
    }

    public ProductionDetailedResponseDTO convertToDetailedDTO(Production entity) {
        ProductionDetailedResponseDTO dto = new ProductionDetailedResponseDTO();
        dto.setProdid(entity.getProdid());
        dto.setProducttype(entity.getProductype());
        dto.setWarehousemanager(entity.getWarehousemanager());
        dto.setMixer(entity.getMixer());
        dto.setDate(entity.getDate());
        dto.setVolume(entity.getVolume());
        dto.setCurrentvolume(entity.getCurrentvolume());
        dto.setStatus(entity.getStatus());
        dto.setExpiredate(entity.getExpiredate());
        return dto;
    }


    //get by id
    public Production findById(Long id) {
        try{
            return productionRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Production not found with id:" + id));
        }catch(Exception e){
            throw new RuntimeException("Failed to find production with id:" + id, e);
        }
    }

    //create
    @Transactional
    public Production createProduction(ProductionRequestDTO dto) {
        try {
            ProductType producttype = productTypeRepository.findById(dto.getPtid())
                    .orElseThrow(() -> new RuntimeException("Product type not found with id:" + dto.getPtid()));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with id:" + dto.getWmid()));

            Mixer mixer = mixerRepository.findById(dto.getMixerid())
                    .orElseThrow(() -> new RuntimeException("Mixer not found with id:" + dto.getMixerid()));

            // Step 1: Get Recipe for Mixer
            Recipe recipe = recipeRepository.findByMixer_Mixerid(dto.getMixerid())
                    .orElseThrow(() -> new RuntimeException("No recipe found for the selected mixer."));

            List<RecipeItem> items = recipeItemRepository.findByRecipe_Recipeid(recipe.getRecipeid());

            // Step 2: Validate availability and deduct quantities
            for (RecipeItem item : items) {
                RawChemicalType rawChemicalType = item.getRawChemical();  // Chemical type from recipe

                // Find all RawChemical inventory entities for this chemical type
                List<RawChemical> rawChemicals = rawChemicalRepository.findByChemicalType(rawChemicalType);

                double requiredQty = item.getQuantity();

                // Sum all volumes from RawChemical entities
                double totalAvailable = rawChemicals.stream().mapToDouble(RawChemical::getVolume).sum();

                if (totalAvailable < requiredQty) {
                    throw new RuntimeException("Insufficient quantity for chemical: " + rawChemicalType.getName());
                }

                // Get batches from SupplierRawMaterial and check batch stock availability
                List<SupplierRawMaterial> batches = supplierRawMaterialRepository.findByChemicalIdOrderByExpAsc(
                        rawChemicalType.getChemid()
                );

                if (!canFulfillQuantity(batches, requiredQty)) {
                    throw new RuntimeException("Not enough batch stock for " + rawChemicalType.getName());
                }

                // Deduct quantity from SupplierRawMaterial batches (FIFO)
                deductFromBatches(batches, requiredQty);

                // Deduct quantity from RawChemical inventory
                deductFromRawChemicals(rawChemicals, requiredQty);
            }

            // Step 3: Save production entity
            Production production = new Production();
            production.setProductype(producttype);
            production.setWarehousemanager(wm);
            production.setMixer(mixer);
            production.setDate(new Date());
            production.setVolume(dto.getVolume());
            production.setCurrentvolume(dto.getCurrentvolume());
            production.setStatus("pending");
            production.setExpiredate(dto.getExpiredate());

            Production saved = productionRepository.save(production);

            mixer.setAvailability(0); // <- This line is newly added
            mixerRepository.save(mixer); // <- This line is newly added

            return saved;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create production: " + e.getMessage(), e);
        }
    }

    private void deductFromRawChemicals(List<RawChemical> rawChemicals, double requiredQty) {
        for (RawChemical rawChem : rawChemicals) {
            double available = rawChem.getVolume();

            if (available >= requiredQty) {
                rawChem.setVolume(available - requiredQty);
                rawChemicalRepository.save(rawChem);
                break;
            } else {
                rawChem.setVolume(0);
                rawChemicalRepository.save(rawChem);
                requiredQty -= available;
            }
        }
    }


    // Helper: Can fulfill
    private boolean canFulfillQuantity(List<SupplierRawMaterial> batches, double requiredQty) {
        double total = 0;
        for (SupplierRawMaterial srm : batches) {
            total += srm.getCurrentQuantity();
            if (total >= requiredQty) return true;
        }
        return false;
    }

    //  Helper: Deduct from batches (FIFO)
    private void deductFromBatches(List<SupplierRawMaterial> batches, double requiredQty) {
        for (SupplierRawMaterial srm : batches) {
            int available = srm.getCurrentQuantity();

            if (available >= requiredQty) {
                srm.setCurrentQuantity((int) (available - requiredQty));
                supplierRawMaterialRepository.save(srm);
                break;
            } else {
                srm.setCurrentQuantity(0);
                supplierRawMaterialRepository.save(srm);
                requiredQty -= available;
            }
        }
    }

    //update by id
    public Production updateProduction(Long id, ProductionRequestDTO dto) {
        try{
            ProductType producttype = productTypeRepository.findById(dto.getPtid())
                    .orElseThrow(()-> new RuntimeException("Product type not found with id:" + dto.getPtid()));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(()-> new RuntimeException("Warehouse not found with id:" + dto.getWmid()));

            Mixer mixer = mixerRepository.findById(dto.getMixerid())
                    .orElseThrow(()-> new RuntimeException("Mixer not found with id:" + dto.getMixerid()));

            Production production = productionRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Production not found with id:" + id));

            production.setProductype(producttype);
            production.setMixer(mixer);
            production.setDate(new Date());// current date should be added
            production.setVolume(dto.getVolume());
            production.setCurrentvolume(dto.getVolume());
            production.setStatus(dto.getStatus());
            production.setExpiredate(dto.getExpiredate());

            return productionRepository.save(production);


        } catch (Exception e) {
            throw new RuntimeException("Failed to update production with id:" + id, e);
        }
    }

    // delete
    public void deleteProduction(Long id) {
        try{
            Production production = productionRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Production not found with id:" + id));

            productionRepository.delete(production);

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete production with id:" + id, e);
        }
    }
    
}
