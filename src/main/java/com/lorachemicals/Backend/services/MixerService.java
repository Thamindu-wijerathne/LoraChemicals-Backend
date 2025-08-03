package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.MixerRequestDTO;
import com.lorachemicals.Backend.model.Mixer;
import com.lorachemicals.Backend.model.ProductType;
import com.lorachemicals.Backend.repository.MixerRepository;
import com.lorachemicals.Backend.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MixerService {

    @Autowired
    private MixerRepository mixerRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    // Get all mixers
    public List<Mixer> getAllMixers() {
        try {
            return mixerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve mixers: " + e.getMessage(), e);
        }
    }

    //get all by product type

    // Get mixer by ID
    public Mixer getMixerById(Long id) {
        try {
            return mixerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mixer not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get mixer by ID: " + e.getMessage(), e);
        }
    }

    // Create mixer
    public Mixer createMixer(MixerRequestDTO dto) {
        try {
            Mixer mixer = new Mixer();
            mixer.setName(dto.getName());
            mixer.setCapacity(dto.getCapacity());
            mixer.setAvailability(1); // default available

            return mixerRepository.save(mixer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mixer: " + e.getMessage(), e);
        }
    }

    // Update mixer
    public Mixer updateMixer(Long id, MixerRequestDTO dto) {
        Mixer mixer = mixerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mixer not found with id: " + id));

        mixer.setName(dto.getName());
        mixer.setCapacity(dto.getCapacity());
        mixer.setAvailability(dto.getAvailability());

        return mixerRepository.save(mixer);
    }


    // Update availability
    public Mixer updateAvailability(Long mixerid, int availability) {
        try {
            Mixer mixer = mixerRepository.findById(mixerid)
                    .orElseThrow(() -> new RuntimeException("Mixer not found with ID: " + mixerid));

            mixer.setAvailability(availability);
            return mixerRepository.save(mixer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update availability: " + e.getMessage(), e);
        }
    }

    // Delete mixer
    public void deleteMixer(Long mixerid) {
        try {
            Mixer mixer = mixerRepository.findById(mixerid)
                    .orElseThrow(() -> new RuntimeException("Mixer not found with ID: " + mixerid));

            mixerRepository.delete(mixer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete mixer: " + e.getMessage(), e);
        }
    }
}
