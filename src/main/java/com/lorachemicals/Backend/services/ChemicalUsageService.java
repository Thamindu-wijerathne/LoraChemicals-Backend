package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.ChemicalUsageRequestDTO;
import com.lorachemicals.Backend.dto.ChemicalUsageResponseDTO;
import com.lorachemicals.Backend.model.ChemicalUsage;
import com.lorachemicals.Backend.model.ChemicalUsageId;
import com.lorachemicals.Backend.model.Production;
import com.lorachemicals.Backend.model.RawChemical;
import com.lorachemicals.Backend.repository.ChemicalUsageRepository;
import com.lorachemicals.Backend.repository.ProductionRepository;
import com.lorachemicals.Backend.repository.RawChemicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChemicalUsageService {

    @Autowired
    private ChemicalUsageRepository chemicalUsageRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private RawChemicalRepository rawChemicalRepository;

    //get all
    public List<ChemicalUsage> getAllChemicalUsage() {
        try{
            return chemicalUsageRepository.findAll();

        } catch(Exception e){
            throw new RuntimeException("Failed to find all chemical usages: " + e.getMessage(), e);
        }
    }

    //get by id
    public ChemicalUsage getChemicalUsageById(Long prodid, Long chemid) {
        try{

            ChemicalUsageId id = new ChemicalUsageId(chemid, prodid);

            return chemicalUsageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Failed to find chemical usage with id: " + id));

        } catch(Exception e){
            throw new RuntimeException("Failed to find chemical usage by id: " + e);
        }
    }

    //create
    public ChemicalUsageResponseDTO addChemicalUsage(ChemicalUsageRequestDTO dto) {
        try{
            Production production = productionRepository.findById(dto.getProdId())
                    .orElseThrow(()-> new RuntimeException("Failed to find prodid."));

            RawChemical rawChemical = rawChemicalRepository.findById(dto.getInventoryId())
                    .orElseThrow(()-> new RuntimeException("Failed to find chemid"));

            ChemicalUsageId id = new ChemicalUsageId(dto.getInventoryId(),dto.getProdId());

            ChemicalUsage cu = new ChemicalUsage();
            cu.setChemicalUsageId(id);
            cu.setProduction(production);
            cu.setChemical(rawChemical);
            cu.setQuantity(dto.getQuantity());

            chemicalUsageRepository.save(cu);

            return convertToDTO(cu);

        } catch(Exception e){
            throw new RuntimeException("Failed to add chemical usage to database: " + e.getMessage(), e);
        }
    }

    //update
    public ChemicalUsageResponseDTO updateChemicalUsage(Long prodid, Long chemid, ChemicalUsageRequestDTO dto) {
        try{
            ChemicalUsageId id = new ChemicalUsageId(chemid, prodid);
            ChemicalUsage cu = chemicalUsageRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Failed to find id."));

            cu.setQuantity(dto.getQuantity());
            chemicalUsageRepository.save(cu);

            return convertToDTO(cu);

        } catch(Exception e){
            throw new RuntimeException("Failed to update chemical usage to database: " + e.getMessage(), e);
        }
    }

    private ChemicalUsageResponseDTO convertToDTO(ChemicalUsage cu) {
        try{
            ChemicalUsageResponseDTO dto = new ChemicalUsageResponseDTO();
            dto.setInventoryid(cu.getChemical().getInventoryid());
            dto.setProdid(cu.getProduction().getProdid());
            dto.setQuantity(cu.getQuantity());
            return dto;

        } catch(Exception e){
            throw new RuntimeException("Failed to convert chemical usage to DTO: " + e.getMessage(), e);
        }
    }

    //delete
    public void deleteChemicalUsage(Long prodid, Long chemid) {
        try {
            ChemicalUsageId id = new ChemicalUsageId(chemid, prodid);

            if(!chemicalUsageRepository.existsById(id)){
                throw new RuntimeException("Failed to find chemical usage with id: " + id);
            }
            chemicalUsageRepository.deleteById(id);

        }catch(Exception e){
            throw new RuntimeException("Failed to delete chemical usage: " + e.getMessage(), e);
        }
    }



}
