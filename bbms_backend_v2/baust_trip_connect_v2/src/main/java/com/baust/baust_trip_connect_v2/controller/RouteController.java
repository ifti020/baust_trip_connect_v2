package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.RouteDTO;
import com.baust.baust_trip_connect_v2.model.Route;
import com.baust.baust_trip_connect_v2.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    // ✅ Add new route (Admin only) → return DTO
    @PostMapping
    public ResponseEntity<RouteDTO> addRoute(@RequestBody Route route) {
        Route saved = routeService.saveRoute(route);
        return ResponseEntity.ok(routeService.toDTO(saved));
    }

    // ✅ Get all routes → return DTO list (User/Admin)
    @GetMapping
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    // ✅ Get route by ID → return DTO (User/Admin)
    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> getRoute(@PathVariable Long id) {
        return routeService.getRouteById(id)
                .map(route -> ResponseEntity.ok(routeService.toDTO(route)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update route info (Admin only) → return DTO
    @PutMapping("/{id}")
    public ResponseEntity<RouteDTO> updateRoute(@PathVariable Long id, @RequestBody Route route) {
        Route updated = routeService.updateRoute(id, route);
        return ResponseEntity.ok(routeService.toDTO(updated));
    }

    // ✅ Delete route (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ NEW: Get route schedule (Morning/Evening times) → User/Admin
    @GetMapping("/{id}/schedule")
    public ResponseEntity<RouteDTO> getRouteSchedule(@PathVariable Long id) {
        return routeService.getRouteById(id)
                .map(route -> ResponseEntity.ok(routeService.toDTO(route)))
                .orElse(ResponseEntity.notFound().build());
    }
}
