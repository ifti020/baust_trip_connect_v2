package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.LocationDTO;
import com.baust.baust_trip_connect_v2.model.Bus;
import com.baust.baust_trip_connect_v2.model.Location;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.repository.BusRepository;
import com.baust.baust_trip_connect_v2.repository.LocationRepository;
import com.baust.baust_trip_connect_v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Save location (DTO → Entity)
    public Location saveLocation(LocationDTO dto, User staff) {
        Bus bus = busRepository.findById(dto.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // Staff validation → শুধু নিজের assigned bus এ পাঠাতে পারবে
        if (staff.getRole() == Role.STAFF &&
                (staff.getBusAssigned() == null || !staff.getBusAssigned().getId().equals(bus.getId()))) {
            throw new RuntimeException("Staff not assigned to this bus");
        }

        Location location = new Location();
        location.setBus(bus);
        location.setUser(staff);
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setSpeed(dto.getSpeed());
        location.setTimestamp(LocalDateTime.now());

        // 🔹 NEW: tripType যোগ করা হলো (MORNING / EVENING)
        location.setTripType(dto.getTripType());

        return locationRepository.save(location);
    }

    // ✅ Mapper: Entity → DTO
    public LocationDTO toDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getBus() != null ? location.getBus().getId() : null,
                location.getBus() != null ? location.getBus().getDisplayName() : null, // 🔹 NEW: Dynamic bus name
                (location.getBus() != null && location.getBus().getRoute() != null)
                        ? location.getBus().getRoute().getRouteName()
                        : null,
                location.getLatitude(),
                location.getLongitude(),
                location.getSpeed(),
                location.getTimestamp(),
                location.getUser() != null ? location.getUser().getName() : null, // ✅ Staff name
                location.getTripType() // 🔹 NEW: MORNING / EVENING tripType DTO তে পাঠানো হচ্ছে
        );
    }

    // ✅ Get all locations → DTO list
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get latest staff locations (Admin/SuperAdmin)
    public List<LocationDTO> getLatestLocationsForAllStaff() {
        List<User> staffList = userRepository.findAllByRole(Role.STAFF);
        return staffList.stream()
                .map(staff -> {
                    Location latest = locationRepository.findTop1ByUserIdOrderByTimestampDesc(staff.getId());
                    return latest != null ? toDTO(latest) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ✅ Get bus location history → recent N entries
    public List<LocationDTO> getRecentBusLocations(Long busId, int limit) {
        return locationRepository.findTop50ByBusIdOrderByTimestampDesc(busId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ NEW: Get latest location by tripType (MORNING/EVENING)
    public LocationDTO getLatestLocationByTripType(Long busId, String tripType) {
        Location latest = locationRepository.findTop1ByBusIdAndTripTypeOrderByTimestampDesc(busId, tripType);
        return latest != null ? toDTO(latest) : null;
    }

    // ✅ Delete location
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
