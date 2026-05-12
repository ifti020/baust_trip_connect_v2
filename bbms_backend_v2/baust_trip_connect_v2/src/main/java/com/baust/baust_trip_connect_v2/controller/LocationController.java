package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.LocationDTO;
import com.baust.baust_trip_connect_v2.model.Location;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.service.LocationService;
import com.baust.baust_trip_connect_v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    // ✅ Staff share location → validate bus assignment + tripType (MORNING/EVENING)
    @PostMapping
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO dto) {
        User current = userService.getCurrentUser();
        if (current != null && current.getRole() == Role.STAFF) {
            if (current.getBusAssigned() != null &&
                    current.getBusAssigned().getId().equals(dto.getBusId())) {
                Location saved = locationService.saveLocation(dto, current);
                return ResponseEntity.ok(locationService.toDTO(saved));
            }
            return ResponseEntity.status(403).body(null); // অন্য bus এ পাঠাতে পারবে না
        }
        return ResponseEntity.status(403).build();
    }

    // ✅ Get latest staff locations (Admin/SuperAdmin only)
    @GetMapping("/staff")
    public ResponseEntity<List<LocationDTO>> getStaffLocations() {
        User current = userService.getCurrentUser();
        if (current != null && (current.getRole() == Role.ADMIN || current.getRole() == Role.SUPER_ADMIN)) {
            return ResponseEntity.ok(locationService.getLatestLocationsForAllStaff());
        }
        return ResponseEntity.status(403).build();
    }

    // ✅ Get bus location history (User/Staff/Admin/SuperAdmin)
    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<LocationDTO>> getLocationsByBus(@PathVariable Long busId) {
        User current = userService.getCurrentUser();
        if (current != null) {
            return ResponseEntity.ok(locationService.getRecentBusLocations(busId, 50));
        }
        return ResponseEntity.status(403).build();
    }

    // ✅ NEW: Get latest bus location by tripType (MORNING/EVENING)
    @GetMapping("/bus/{busId}/trip/{tripType}")
    public ResponseEntity<LocationDTO> getLatestLocationByTripType(@PathVariable Long busId,
                                                                   @PathVariable String tripType) {
        User current = userService.getCurrentUser();
        if (current != null) {
            LocationDTO latest = locationService.getLatestLocationByTripType(busId, tripType.toUpperCase());
            if (latest != null) {
                return ResponseEntity.ok(latest);
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(403).build();
    }

    // ✅ Delete location (Admin/SuperAdmin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        User current = userService.getCurrentUser();
        if (current != null && (current.getRole() == Role.ADMIN || current.getRole() == Role.SUPER_ADMIN)) {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(403).build();
    }
}
