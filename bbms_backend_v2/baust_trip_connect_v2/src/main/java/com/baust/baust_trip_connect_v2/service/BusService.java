package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.BusDTO;
import com.baust.baust_trip_connect_v2.model.Bus;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.Route;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.repository.BusRepository;
import com.baust.baust_trip_connect_v2.repository.RouteRepository;
import com.baust.baust_trip_connect_v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Save bus (Route auto-create + default status)
    public Bus saveBus(Bus bus) {
        if (bus.getRoute() != null && bus.getRoute().getRouteName() != null) {
            String routeName = bus.getRoute().getRouteName();

            // Route খুঁজে বের করো, না থাকলে নতুন বানাও
            Route route = routeRepository.findByRouteName(routeName)
                    .orElseGet(() -> {
                        Route newRoute = new Route();
                        newRoute.setRouteName(routeName);
                        return routeRepository.save(newRoute);
                    });

            bus.setRoute(route);
        }

        // Default status
        if (bus.getStatus() == null) {
            bus.setStatus("Active");
        }

        return busRepository.save(bus);
    }

    // ✅ Mapper: Entity → DTO (Dynamic bus name সহ)
    public BusDTO toDTO(Bus bus) {
        String displayName = bus.getRoute() != null
                ? bus.getRoute().getRouteName() + " " + bus.getBusNumber()
                : bus.getBusNumber();

        return new BusDTO(
                bus.getId(),
                displayName,
                bus.getRoute() != null ? bus.getRoute().getRouteName() : null,
                bus.getStatus()
        );
    }

    // ✅ Get all buses
    public List<BusDTO> getAllBuses() {
        return busRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get bus by ID
    public Optional<Bus> getBusById(Long id) {
        return busRepository.findById(id);
    }

    // ✅ Update bus info
    public Bus updateBus(Long id, Bus busDetails) {
        Bus bus = busRepository.findById(id).orElseThrow();
        bus.setBusNumber(busDetails.getBusNumber());
        bus.setStatus(busDetails.getStatus());

        if (busDetails.getRoute() != null && busDetails.getRoute().getRouteName() != null) {
            String routeName = busDetails.getRoute().getRouteName();
            Route route = routeRepository.findByRouteName(routeName)
                    .orElseGet(() -> {
                        Route newRoute = new Route();
                        newRoute.setRouteName(routeName);
                        return routeRepository.save(newRoute);
                    });
            bus.setRoute(route);
        }

        return busRepository.save(bus);
    }

    // ✅ Delete bus
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    // ✅ Update bus status
    public Bus updateBusStatus(Long id, String status) {
        Bus bus = busRepository.findById(id).orElseThrow();
        bus.setStatus(status);
        return busRepository.save(bus);
    }

    // ✅ Get all bus display names
    public List<String> getAllBusDisplayNames() {
        return busRepository.findAll()
                .stream()
                .map(bus -> bus.getRoute().getRouteName() + " " + bus.getBusNumber())
                .collect(Collectors.toList());
    }

    // ✅ NEW: Assign bus to staff
    public User assignBusToStaff(Long staffId, Long busId) {
        Bus bus = busRepository.findById(busId).orElseThrow();
        User staff = userRepository.findById(staffId).orElseThrow();

        // Staff এর role check করা ভালো হবে
        if (staff.getRole() != Role.STAFF) {
            throw new IllegalArgumentException("User " + staff.getEmail() + " is not a staff");
        }

        // Staff এর সাথে bus link করো
        staff.setBusAssigned(bus);
        return userRepository.save(staff);
    }
}
