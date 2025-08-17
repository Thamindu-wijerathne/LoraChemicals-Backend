package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.ParentBatchType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.repository.ParentBatchTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentBatchTypeService {

    @Autowired
    private ParentBatchTypeRepository parentBatchTypeRepository;

    public List<ParentBatchType> getAllParentBatchTypes() {
        return parentBatchTypeRepository.findAll();
    }

    public Optional<ParentBatchType> getParentBatchTypeById(Long id) {
        return parentBatchTypeRepository.findById(id);
    }

    public List<ParentBatchType> getParentBatchTypesByProductTypeVolume(ProductTypeVolume ptv) {
        return parentBatchTypeRepository.findByProductTypeVolume(ptv);
    }

    public Optional<ParentBatchType> getParentBatchTypeByUniqueBatchCode(String uniqueBatchCode) {
        return parentBatchTypeRepository.findByUniqueBatchCode(uniqueBatchCode);
    }

    public boolean existsByUniqueBatchCode(String uniqueBatchCode) {
        return parentBatchTypeRepository.existsByUniqueBatchCode(uniqueBatchCode);
    }

    public ParentBatchType saveParentBatchType(ParentBatchType parentBatchType) {
        return parentBatchTypeRepository.save(parentBatchType);
    }

    public void deleteParentBatchType(Long id) {
        parentBatchTypeRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return parentBatchTypeRepository.existsById(id);
    }
}
