package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.BusDTO;
import com.baust.baust_trip_connect_v2.model.Bus;
import com.baust.baust_trip_connect_v2.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    // ✅ Add new bus (Admin only)
    @PostMapping
    public ResponseEntity<BusDTO> addBus(@RequestBody Bus bus) {
        Bus saved = busService.saveBus(bus);
        return ResponseEntity.ok(busService.toDTO(saved));
    }

    // ✅ Get all buses → return DTO list (User/Admin)
    @GetMapping
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    // ✅ Get bus by ID → return DTO (User/Admin)
    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getBus(@PathVariable Long id) {
        return busService.getBusById(id)
                .map(bus -> ResponseEntity.ok(busService.toDTO(bus)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update bus info (Admin only)
    @PutMapping("/{id}")
    public ResponseEntity<BusDTO> updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        Bus updated = busService.updateBus(id, bus);
        return ResponseEntity.ok(busService.toDTO(updated));
    }

    // ✅ Delete bus (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Update bus status (Active / Inactive / Maintenance) → Admin/SuperAdmin
    @PutMapping("/{id}/status")
    public ResponseEntity<BusDTO> updateBusStatus(@PathVariable Long id, @RequestBody String status) {
        Bus updated = busService.updateBusStatus(id, status);
        return ResponseEntity.ok(busService.toDTO(updated));
    }

    // ✅ NEW: Get all bus display names (Dynamic names for User Flow table)
    @GetMapping("/display-names")
    public ResponseEntity<List<String>> getAllBusDisplayNames() {
        return ResponseEntity.ok(busService.getAllBusDisplayNames());
    }
}
