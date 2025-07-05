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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductTypeVolumeService {

    private static final Logger logger = LoggerFactory.getLogger(ProductTypeVolumeService.class);

    private final ProductTypeVolumeRepository repository;
    private final ProductTypeRepository productTypeRepo;
    private final BottletypeRepository bottleRepo;
    private final LabeltypeRepository labelRepo;

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

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

    public ProductTypeVolumeResponseDTO addProductvolumetype(
            ProductTypeVolumeRequestDTO dto,
            MultipartFile imageFile,
            String uploadDir) throws IOException {

        // Validate and fetch related entities
        ProductType productType = productTypeRepo.findById(dto.getPtid())
                .orElseThrow(() -> new IllegalArgumentException("Product type not found with ID: " + dto.getPtid()));

        Bottletype bottletype = bottleRepo.findById(dto.getBottleid())
                .orElseThrow(() -> new IllegalArgumentException("Bottle type not found with ID: " + dto.getBottleid()));

        Labeltype labeltype = labelRepo.findById(dto.getLabelid())
                .orElseThrow(() -> new IllegalArgumentException("Label type not found with ID: " + dto.getLabelid()));

        // Create ProductTypeVolume entity
        ProductTypeVolume product = new ProductTypeVolume();
        product.setName(dto.getName());
        product.setProductType(productType);
        product.setVolume(dto.getVolume());
        product.setUnitPrice(BigDecimal.valueOf(dto.getUnitprice()));
        product.setCategory(dto.getCategory());
        product.setBottletype(bottletype);
        product.setLabeltype(labeltype);

        // Handle image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImageFile(imageFile, uploadDir, dto.getName());
            product.setImage(imagePath);
        }

        // Save to database
        ProductTypeVolume savedProduct = repository.save(product);

        // Convert to response DTO
        return toDto(savedProduct);
    }

    public ProductTypeVolumeResponseDTO update(Long id, ProductTypeVolumeRequestDTO dto, MultipartFile imageFile, String uploadDir) throws IOException {
        Optional<ProductTypeVolume> opt = repository.findById(id);
        if (opt.isEmpty()) return null;

        ProductTypeVolume entity = opt.get();
        fillEntityFromDto(entity, dto);

        // Handle image update if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImageFile(imageFile, uploadDir, dto.getName());
            entity.setImage(imagePath);
        }

        return toDto(repository.save(entity));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;

        // Get the entity to delete associated image file
        Optional<ProductTypeVolume> entity = repository.findById(id);
        if (entity.isPresent() && entity.get().getImage() != null) {
            try {
                // Delete image file from filesystem
                Path imagePath = Paths.get("uploads/images").resolve(entity.get().getImage());
                Files.deleteIfExists(imagePath);
                logger.info("Deleted image file: {}", imagePath);
            } catch (IOException e) {
                logger.error("Error deleting image file: {}", e.getMessage());
                // Continue with entity deletion even if image deletion fails
            }
        }

        repository.deleteById(id);
        return true;
    }

    private String saveImageFile(MultipartFile imageFile, String uploadDir, String productName) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            logger.info("Created upload directory: {}", uploadDir);
        }

        // Generate unique filename
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String sanitizedProductName = productName.replaceAll("[^a-zA-Z0-9]", "_");
        String uniqueFilename = sanitizedProductName + "_" + System.currentTimeMillis() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        logger.info("Image saved successfully: {}", filePath.toString());
        return uniqueFilename; // Return only filename, not full path
    }

    private void fillEntityFromDto(ProductTypeVolume entity, ProductTypeVolumeRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setUnitPrice(BigDecimal.valueOf(dto.getUnitprice()));
        entity.setCategory(dto.getCategory());
        entity.setVolume(dto.getVolume());

        // Fetch and set related entities
        ProductType productType = productTypeRepo.findById(dto.getPtid())
                .orElseThrow(() -> new IllegalArgumentException("Product type not found with ID: " + dto.getPtid()));
        entity.setProductType(productType);

        Bottletype bottletype = bottleRepo.findById(dto.getBottleid())
                .orElseThrow(() -> new IllegalArgumentException("Bottle type not found with ID: " + dto.getBottleid()));
        entity.setBottletype(bottletype);

        Labeltype labeltype = labelRepo.findById(dto.getLabelid())
                .orElseThrow(() -> new IllegalArgumentException("Label type not found with ID: " + dto.getLabelid()));
        entity.setLabeltype(labeltype);
    }

    private ProductTypeVolumeResponseDTO toDto(ProductTypeVolume ptv) {
        String imageUrl = null;
        if (ptv.getImage() != null && !ptv.getImage().isEmpty()) {
            imageUrl = baseUrl + "/producttypevolumes/images/" + ptv.getImage();
        }

        return new ProductTypeVolumeResponseDTO(
                ptv.getPtvid(),
                ptv.getName(),
                ptv.getProductType() != null ? ptv.getProductType().getProductTypeId() : null,
                ptv.getProductType() != null ? ptv.getProductType().getName() : null,
                ptv.getVolume(),
                ptv.getUnitPrice(),
                imageUrl,
                ptv.getCategory(),
                ptv.getBottletype() != null ? ptv.getBottletype().getBottleid() : null,
                ptv.getBottletype() != null ? ptv.getBottletype().getName() : null,
                ptv.getLabeltype() != null ? ptv.getLabeltype().getLabelid() : null,
                ptv.getLabeltype() != null ? ptv.getLabeltype().getName() : null
        );
    }
}