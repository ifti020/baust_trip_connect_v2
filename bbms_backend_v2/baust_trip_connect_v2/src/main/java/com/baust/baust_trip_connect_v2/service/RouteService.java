package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.RouteDTO;
import com.baust.baust_trip_connect_v2.model.Route;
import com.baust.baust_trip_connect_v2.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    // ✅ Save route (Admin will set fixed schedule)
    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    // ✅ Mapper: Entity → DTO (Fixed schedule সহ)
    public RouteDTO toDTO(Route route) {
        return new RouteDTO(
                route.getId(),
                route.getRouteName(),
                route.getMorningDeparture() != null ? route.getMorningDeparture().toString() : null, // 🔹 NEW
                route.getMorningArrival() != null ? route.getMorningArrival().toString() : null,     // 🔹 NEW
                route.getAfternoonDeparture() != null ? route.getAfternoonDeparture().toString() : null, // 🔹 NEW
                route.getAfternoonArrival() != null ? route.getAfternoonArrival().toString() : null      // 🔹 NEW
        );
    }

    // ✅ Get all routes
    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get route by ID
    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    // ✅ Update route (Admin can update schedule)
    public Route updateRoute(Long id, Route routeDetails) {
        Route route = routeRepository.findById(id).orElseThrow();
        route.setRouteName(routeDetails.getRouteName());
        route.setMorningDeparture(routeDetails.getMorningDeparture());   // 🔹 NEW
        route.setMorningArrival(routeDetails.getMorningArrival());       // 🔹 NEW
        route.setAfternoonDeparture(routeDetails.getAfternoonDeparture());// 🔹 NEW
        route.setAfternoonArrival(routeDetails.getAfternoonArrival());   // 🔹 NEW
        return routeRepository.save(route);
    }

    // ✅ Delete route
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}
