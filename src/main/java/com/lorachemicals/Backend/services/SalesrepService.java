package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.model.SalesRep;
import com.lorachemicals.Backend.repository.SalesRepRepository;
import org.springframework.stereotype.Service;

@Service
public class SalesrepService {
    private final SalesRepRepository salesrepRepo;

    public SalesrepService(SalesRepRepository salesrepRepo) {
        this.salesrepRepo = salesrepRepo;
    }

    public void saveSalesRep(SalesRep salesRep) {
        salesrepRepo.save(salesRep);
    }

    public void deleteByUserId(Long userId) {
        SalesRep rep = salesrepRepo.findByUserId(userId);
        if (rep != null) {
            salesrepRepo.delete(rep);
        }
    }

}
