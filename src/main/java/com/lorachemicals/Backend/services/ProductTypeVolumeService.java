package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.ProductTypeVolumeResponseDTO;
import com.lorachemicals.Backend.model.ProductTypeVolume;
import com.lorachemicals.Backend.repository.ProductTypeVolumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeVolumeService {

    private final ProductTypeVolumeRepository repository;

    public ProductTypeVolumeService(ProductTypeVolumeRepository repository) {
        this.repository = repository;
    }


    public List<ProductTypeVolumeResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductTypeVolumeResponseDTO toDto(ProductTypeVolume ptv) {
        return new ProductTypeVolumeResponseDTO(
                ptv.getPtvid(),
                ptv.getProductType().getName(),
                ptv.getUnitprice(),
                ptv.getImage(),
                ptv.getCatergory(),
                ptv.getBottletype().getBottleid(),
                ptv.getVolume(),
                ptv.getLabeltype().getLabelid(),
                ptv.getProductType().getProductTypeId(),  // or correct getter for ProductType id
                ptv.getProductType().getDetails()  // fetching details from ProductType table via relation
        );
    }




}
