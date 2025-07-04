package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.ProductTypeVolumeRequestDTO;
import com.lorachemicals.Backend.dto.ProductTypeVolumeResponseDTO;
import com.lorachemicals.Backend.model.Bottletype;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.repository.BottletypeRepository;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import com.lorachemicals.Backend.repository.ProductTypeRepository;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    public ProductTypeVolumeResponseDTO addProductvolumetype(ProductTypeVolumeRequestDTO dto,
                                                             MultipartFile imageFile, String uploadDir) throws IOException {
        ProductTypeVolume entity = new ProductTypeVolume();
        // Set basic fields and relations
        fillEntityFromDto(entity, dto);

        String imageFilename = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFilename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.]", "_");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            File file = new File(dir, imageFilename);
            imageFile.transferTo(file);
        }
        entity.setImage(imageFilename);

        ProductTypeVolume saved = repository.save(entity);
        return toDto(saved);
    }

    public ProductTypeVolumeResponseDTO update(Long id, ProductTypeVolumeRequestDTO dto, String imageFilename) {
        Optional<ProductTypeVolume> opt = repository.findById(id);
        if (opt.isEmpty()) return null;
        ProductTypeVolume entity = opt.get();
        fillEntityFromDto(entity, dto);
        // Optionally update image filename if provided
        if (imageFilename != null) {
            entity.setImage(imageFilename);
        }
        return toDto(repository.save(entity));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    // FIX: Map all fields properly including name and corrected category
    private void fillEntityFromDto(ProductTypeVolume entity, ProductTypeVolumeRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setUnitprice(dto.getUnitprice());
        entity.setCategory(dto.getCategory());
        entity.setVolume(dto.getVolume());
        Bottletype bottle = bottleRepo.findById(dto.getBottleid()).orElse(null);
        entity.setBottletype(bottle);
        Labeltype label = labelRepo.findById(dto.getLabelid()).orElse(null);
        entity.setLabeltype(label);
        ProductType pt = productTypeRepo.findById(dto.getPtid()).orElse(null);
        entity.setProductType(pt);
    }

    private ProductTypeVolumeResponseDTO toDto(ProductTypeVolume ptv) {
        String imageUrl = ptv.getImage() != null
                ? "/producttypevolumes/images/" + ptv.getImage()
                : null;

        return new ProductTypeVolumeResponseDTO(
                ptv.getPtvid(),
                ptv.getName(),
                ptv.getProductType() != null ? ptv.getProductType().getProductTypeId() : null,
                ptv.getProductType() != null ? ptv.getProductType().getName() : null,
                ptv.getVolume(),
                ptv.getUnitprice(),
                imageUrl,
                ptv.getCategory(),
                ptv.getBottletype() != null ? ptv.getBottletype().getBottleid() : null,
                ptv.getBottletype() != null ? ptv.getBottletype().getName() : null,
                ptv.getLabeltype() != null ? ptv.getLabeltype().getLabelid() : null,
                ptv.getLabeltype() != null ? ptv.getLabeltype().getName() : null
        );
    }
}