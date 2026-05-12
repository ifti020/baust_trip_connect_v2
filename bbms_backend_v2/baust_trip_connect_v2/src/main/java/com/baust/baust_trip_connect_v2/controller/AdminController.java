package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ Assign staff (role change USER → STAFF)
    @PutMapping("/assign-staff/{userId}")
    public ResponseEntity<UserDTO> assignStaff(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.assignStaff(userId));
    }

    // ✅ Remove staff (role reset STAFF → USER)
    @PutMapping("/remove-staff/{userId}")
    public ResponseEntity<UserDTO> removeStaff(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.removeStaff(userId));
    }

    // ✅ Assign bus to staff (User.busAssigned আপডেট হবে)
    @PutMapping("/assign-bus/{staffId}/{busId}")
    public ResponseEntity<UserDTO> assignBus(@PathVariable Long staffId,
                                             @PathVariable Long busId) {
        // সরাসরি service থেকে UserDTO ফেরত নাও
        return ResponseEntity.ok(adminService.assignBus(staffId, busId));
    }

    // ✅ Admin bus status update
    @PutMapping("/buses/{busId}/status")
    public ResponseEntity<Void> updateBusStatus(@PathVariable Long busId,
                                                @RequestParam String status) {
        adminService.updateBusStatus(busId, status);
        return ResponseEntity.ok().build();
    }
}
