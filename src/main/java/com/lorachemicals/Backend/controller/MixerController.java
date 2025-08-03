package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.dto.MixerRequestDTO;
import com.lorachemicals.Backend.model.Mixer;
import com.lorachemicals.Backend.services.MixerService;
import com.lorachemicals.Backend.util.AccessControlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mixer")
public class MixerController {

    @Autowired
    private MixerService mixerService;

    // Get all mixers
    @GetMapping("/all")
    public ResponseEntity<?> getAllMixers(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin","warehouse");
        try {
            List<Mixer> mixers = mixerService.getAllMixers();
            return new ResponseEntity<>(mixers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get mixers: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/pt/{id}")
//    public ResponseEntity<?> getAllByPT(@PathVariable Long id, HttpServletRequest request) {
//        AccessControlUtil.checkAccess(request, "admin", "warehouse");
//        try{
//            List<Mixer> mixers = mixerService.getMixerByProductType(id);
//            return new ResponseEntity<>(mixers, HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>("Failed to get mixers by ptid: " + e.getMessage(),
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // Get mixer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMixerById(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Mixer mixer = mixerService.getMixerById(id);
            return new ResponseEntity<>(mixer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get mixer: " + e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
    }

    // Create mixer
    @PostMapping("/add")
    public ResponseEntity<?> createMixer(@RequestBody MixerRequestDTO dto, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Mixer mixer = mixerService.createMixer(dto);
            return new ResponseEntity<>(mixer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create mixer: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update mixer
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMixer(@PathVariable Long id, @RequestBody MixerRequestDTO dto) {
        try {
            Mixer mixer = mixerService.updateMixer(id, dto);
            return new ResponseEntity<>(mixer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update mixer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Update availability
    @PatchMapping("/availability/{id}")
    public ResponseEntity<?> updateAvailability(@PathVariable Long id, @RequestParam int availability, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            Mixer mixer = mixerService.updateAvailability(id, availability);
            return new ResponseEntity<>(mixer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update availability: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete mixer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMixer(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "admin");
        try {
            mixerService.deleteMixer(id);
            return new ResponseEntity<>("Mixer deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete mixer: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}