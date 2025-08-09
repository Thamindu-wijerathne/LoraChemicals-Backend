package com.lorachemicals.Backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lorachemicals.Backend.dto.SalesRepResponseDTO;
import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.model.User;
import com.lorachemicals.Backend.repository.SalesRepRepository;

@Service
public class SalesrepService {
    private final SalesRepRepository salesrepRepo;

    public SalesrepService(SalesRepRepository salesrepRepo) {
        this.salesrepRepo = salesrepRepo;
    }

    public void saveSalesRep(SalesRep salesRep) {
        salesrepRepo.save(salesRep);
    }

    public SalesRep createSalesRep(User user) {
        SalesRep salesRep = new SalesRep(user); // This will set initial status to 1
        return salesrepRepo.save(salesRep);
    }

    public SalesRep findById(Long id) {
        return salesrepRepo.findByUserId(id);
    }

    public void deleteByUserId(Long userId) {
        SalesRep rep = salesrepRepo.findByUserId(userId);
        if (rep != null) {
            salesrepRepo.delete(rep);
        }
    }
    
    public SalesRep getSalesRepById(Long Id) {
        return salesrepRepo.findByUserId(Id);
    }

    public List<SalesRep> getAllSalesreps() {
        return salesrepRepo.findAll();
    }

    public List<SalesRep> getActiveSalesReps() {
        return salesrepRepo.findAll().stream()
                .filter(salesRep -> salesRep.getStatus() == 1)
                .collect(Collectors.toList());
    }

    public List<SalesRepResponseDTO> getAllSalesRepsWithDetails() {
        List<SalesRep> salesReps = salesrepRepo.findAll();
        return salesReps.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public SalesRep updateSalesRepStatus(Long srepid, int status) {
        Optional<SalesRep> salesRepOpt = salesrepRepo.findById(srepid);
        if (salesRepOpt.isPresent()) {
            SalesRep salesRep = salesRepOpt.get();
            salesRep.setStatus(status);
            return salesrepRepo.save(salesRep);
        }
        return null;
    }

    private SalesRepResponseDTO mapToResponseDTO(SalesRep salesRep) {
        User user = salesRep.getUser();
        return new SalesRepResponseDTO(
                salesRep.getSrepid(),
                user.getId(),
                user.getFname(),
                user.getLname(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getNic(),
                salesRep.getStatus(),
                user.getStatus()
        );
    }
}
