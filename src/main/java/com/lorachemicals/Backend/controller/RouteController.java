package com.lorachemicals.Backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lorachemicals.Backend.dto.RouteDTO;
import com.lorachemicals.Backend.model.Route;
import com.lorachemicals.Backend.services.RouteService;
import com.lorachemicals.Backend.util.AccessControlUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Route>> getAllRoutes(HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin","warehouse");
        // You can add role checks if needed, e.g.
        // AccessControlUtil.checkAccess(request, "admin", "salesrep");
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRouteById(@PathVariable Long id) {
        Route route = routeService.getRouteById(id);
        if (route != null) {
            return ResponseEntity.ok(route);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRoute(@RequestBody RouteDTO routeDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin");

        try{
            Route route = routeService.addRoute(routeDTO);
            return new ResponseEntity<>(route, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to add route", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin");
        try{
            Route route = routeService.updateRoute(id, routeDTO);
            return new ResponseEntity<>(route, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Failed to update route", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoute(@PathVariable Long id, HttpServletRequest request) {
        AccessControlUtil.checkAccess(request, "salesrep", "admin");

        try{
            routeService.deleteRoute(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>("Failed to delete route", HttpStatus.BAD_REQUEST);
        }
    }
}