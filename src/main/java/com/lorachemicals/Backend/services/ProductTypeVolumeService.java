package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.ProductTypeVolumeRequestDTO;
import com.lorachemicals.Backend.dto.ProductTypeVolumeResponseDTO;
import com.lorachemicals.Backend.model.*;
import com.lorachemicals.Backend.repository.BottletypeRepository;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import com.lorachemicals.Backend.repository.ProductTypeRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductTypeVolumeService {

    private final ProductTypeVolumeRepository repository;
    private final ProductTypeRepository productTypeRepo;
    private final BottletypeRepository bottleRepo;
    private final LabeltypeRepository labelRepo;

    public ProductTypeVolumeService(ProductTypeVolumeRepository repository,
                                    ProductTypeRepository productTypeRepo,
                                    BottletypeRepository bottleRepo,
                                    LabeltypeRepository labelRepo) {
        this.repository = repository;
        this.productTypeRepo = productTypeRepo;
        this.bottleRepo = bottleRepo;
        this.labelRepo = labelRepo;
    }

    public List<ProductTypeVolumeResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductTypeVolumeResponseDTO getById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    public ProductTypeVolumeResponseDTO create(ProductTypeVolumeRequestDTO dto, String imagePath) {
        ProductTypeVolume entity = new ProductTypeVolume();
        fillEntityFromDto(entity, dto, imagePath);
        return toDto(repository.save(entity));
    }

    public ProductTypeVolumeResponseDTO update(Long id, ProductTypeVolumeRequestDTO dto, String imagePath) {
        Optional<ProductTypeVolume> opt = repository.findById(id);
        if (opt.isEmpty()) return null;
        ProductTypeVolume entity = opt.get();
        fillEntityFromDto(entity, dto, imagePath);
        return toDto(repository.save(entity));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    private void fillEntityFromDto(ProductTypeVolume entity, ProductTypeVolumeRequestDTO dto, String imagePath) {
        entity.setUnitprice(dto.getUnitprice());
        entity.setCategory(dto.getCategory());
        entity.setVolume(dto.getVolume());
        entity.setBottletype(bottleRepo.findById(dto.getBottleid()).orElse(null));
        entity.setLabeltype(labelRepo.findById(dto.getLabelid()).orElse(null));
        entity.setProductType(productTypeRepo.findById(dto.getPtid()).orElse(null));
        if (imagePath != null) {
            entity.setImage(imagePath);
        }
    }

    private ProductTypeVolumeResponseDTO toDto(ProductTypeVolume ptv) {
        return new ProductTypeVolumeResponseDTO(
                ptv.getPtvid(),
                ptv.getProductType() != null ? ptv.getProductType().getName() : null,
                ptv.getUnitprice(),
                ptv.getImage(),
                ptv.getCategory(),
                ptv.getBottletype() != null ? ptv.getBottletype().getBottleid() : null,
                ptv.getVolume(),
                ptv.getLabeltype() != null ? ptv.getLabeltype().getLabelid() : null,
                ptv.getProductType() != null ? ptv.getProductType().getProductTypeId() : null
        );
    }
}
