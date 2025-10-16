package com.lorachemicals.Backend.controller;

import com.lorachemicals.Backend.model.Route;
import com.lorachemicals.Backend.services.RouteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;

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
}