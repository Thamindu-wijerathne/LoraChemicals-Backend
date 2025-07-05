package com.lorachemicals.Backend.services;


import com.lorachemicals.Backend.dto.ProductionRequestDTO;
import com.lorachemicals.Backend.model.Mixer;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.Production;
import com.lorachemicals.Backend.model.WarehouseManager;
import com.lorachemicals.Backend.repository.MixerRepository;
import com.lorachemicals.Backend.repository.ProductTypeRepository;
import com.lorachemicals.Backend.repository.ProductionRepository;
import com.lorachemicals.Backend.repository.WarehouseManagerRepository;
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

    //get all
    public List<Production> findAll() {
        try{
            return productionRepository.findAll();
        }catch(Exception e){
            throw new RuntimeException("Failed to find all productions: " + e.getMessage(), e);
        }
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

    //create a production
    public Production createProduction(ProductionRequestDTO dto) {
        try{
            ProductType producttype = productTypeRepository.findById(dto.getPtid())
                    .orElseThrow(()-> new RuntimeException("Product type not found with id:" + dto.getPtid()));

            WarehouseManager wm = warehouseManagerRepository.findById(dto.getWmid())
                    .orElseThrow(()-> new RuntimeException("Warehouse not found with id:" + dto.getWmid()));

            Mixer mixer = mixerRepository.findById(dto.getMixerid())
                    .orElseThrow(()-> new RuntimeException("Mixer not found with id:" + dto.getMixerid()));

            Production production = new Production();
            production.setProductype(producttype);
            production.setWarehousemanager(wm);
            production.setMixer(mixer);
            production.setDate(new Date()); // current date should be added
            production.setVolume(dto.getVolume());
            production.setCurrentvolume(dto.getCurrentvolume());
            production.setStatus("pending");

            return productionRepository.save(production);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create production: " + e.getMessage(), e);
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
            production.setCurrentvolume(dto.getCurrentvolume());
            production.setStatus(dto.getStatus());

            return productionRepository.save(production);


        } catch (Exception e) {
            throw new RuntimeException("Failed to update production with id:" + id, e);
        }
    }

    //update current volume
    public Production updatecvolume(Long id, ProductionRequestDTO dto) {
        try{
            Production production = productionRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Production not found with id:" + id));

            production.setCurrentvolume(dto.getCurrentvolume());
            production.setStatus(dto.getStatus());
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
