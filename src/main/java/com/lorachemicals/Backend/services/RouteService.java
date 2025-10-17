package com.lorachemicals.Backend.services;

import com.lorachemicals.Backend.dto.RouteDTO;
import com.lorachemicals.Backend.model.Route;
import com.lorachemicals.Backend.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    //create
    public Route addRoute(RouteDTO routeDTO) {
        try{
            Route route = new Route();
            route.setDistrict(routeDTO.getDistrict());

            return routeRepository.save(route);
        }catch(Exception e){
            throw new RuntimeException("Failed to add route" + e.getMessage(), e);
        }
    }

    //update
    public Route updateRoute(Long id, RouteDTO routeDTO) {
        try{
            Route route = routeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Route not found with id: " + id ));

            route.setDistrict(routeDTO.getDistrict());
            return routeRepository.save(route);

        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update route" + e.getMessage(), e);
        }
    }

    //delete
    public void deleteRoute(Long id){
        try{
            Route route = routeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Route not found with id: " + id ));

            routeRepository.delete(route);
        }catch(RuntimeException e){
            throw new RuntimeException("Failed to delete route" + e.getMessage(), e);
        }
    }
}
