package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.Label;
import com.lorachemicals.Backend.model.Labeltype;
import com.lorachemicals.Backend.repository.LabelRepository;
import com.lorachemicals.Backend.repository.LabeltypeRepository;
import com.lorachemicals.Backend.dto.LabelRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabeltypeRepository labeltypeRepository;

    // Get all labels
    public List<Label> getAllLabels() {
        try {
            return labelRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch labels: " + e.getMessage(), e);
        }
    }

    // Get label by inventory ID
    public Optional<Label> getLabelById(Long inventoryId) {
        try {
            return labelRepository.findById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch label by ID: " + e.getMessage(), e);
        }
    }

    // Create new label
    public Label createLabel(LabelRequestDTO dto) {
        try {
            Labeltype labeltype = labeltypeRepository.findById(dto.getLabelTypeId())
                    .orElseThrow(() -> new RuntimeException("Label type not found"));

            Label label = new Label();
            label.setLabeltype(labeltype);
            label.setQuantity(dto.getQuantity());
            label.setLocation(dto.getLocation());
            // Set other RawMaterial fields if needed

            return labelRepository.save(label);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create label: " + e.getMessage(), e);
        }
    }

    // Update existing label
    public Label updateLabel(Long inventoryId, LabelRequestDTO dto) {
        try {
            Label label = labelRepository.findById(inventoryId)
                    .orElseThrow(() -> new RuntimeException("Label not found"));

            Labeltype labeltype = labeltypeRepository.findById(dto.getLabelTypeId())
                    .orElseThrow(() -> new RuntimeException("Label type not found"));

            label.setLabeltype(labeltype);
            label.setQuantity(dto.getQuantity());
            // Update other RawMaterial fields if needed

            return labelRepository.save(label);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update label: " + e.getMessage(), e);
        }
    }

    // Delete label by inventory ID
    public void deleteLabel(Long inventoryId) {
        try {
            labelRepository.deleteById(inventoryId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete label: " + e.getMessage(), e);
        }
    }

    // Sum of quantities grouped by label type
    public List<Object[]> getTotalQuantityGroupedByLabelType() {
        try {
            return labelRepository.sumQuantityGroupedByLabeltype();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get quantity sums: " + e.getMessage(), e);
        }
    }
}
