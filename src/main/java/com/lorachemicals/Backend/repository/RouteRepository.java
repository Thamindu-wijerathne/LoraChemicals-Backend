package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    // You can add custom queries if needed, for example:
    Route findByDistrict(String district);
}
