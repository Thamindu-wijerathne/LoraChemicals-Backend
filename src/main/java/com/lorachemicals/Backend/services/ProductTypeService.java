package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.ProductTypeRequestDto;
import com.lorachemicals.Backend.dto.ProductTypeResponseDto;
import com.lorachemicals.Backend.exception.ResourceNotFoundException;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {

    private final ProductTypeRepository repository;

    public ProductTypeService(ProductTypeRepository repository) {
        this.repository = repository;
    }

    public ProductTypeResponseDto create(ProductTypeRequestDto dto) {
        ProductType productType = new ProductType(dto.getName(), dto.getDetails());
        ProductType saved = repository.save(productType);
        return toDto(saved);
    }

    public ProductTypeResponseDto update(Long id, ProductTypeRequestDto dto) {
        ProductType existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductType not found with id: " + id));

        existing.setName(dto.getName());
        existing.setDetails(dto.getDetails());

        ProductType updated = repository.save(existing);
        return toDto(updated);
    }

    public ProductTypeResponseDto getById(Long id) {
        ProductType pt = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductType not found with id: " + id));
        return toDto(pt);
    }

    public List<ProductTypeResponseDto> getAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        ProductType pt = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductType not found with id: " + id));
        repository.delete(pt);
    }

    private ProductTypeResponseDto toDto(ProductType productType) {
        return new ProductTypeResponseDto(productType.getProductTypeId(), productType.getName(), productType.getDetails());
    }
}
