package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.model.Location;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Register new user → return DTO
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        User saved = userService.saveUser(user);
        return ResponseEntity.ok(userService.toDTO(saved));
    }

    // ✅ Get all users → return DTO list
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Get user by id → return DTO
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(userService.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update user → return DTO
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(userService.toDTO(updated));
    }

    // ✅ Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get current staff profile (role check)
    @GetMapping("/staff/profile")
    public ResponseEntity<UserDTO> getStaffProfile() {
        User staff = userService.getCurrentUser(); // JWT থেকে current user বের করবে
        if (staff != null && staff.getRole() == Role.STAFF) {
            return ResponseEntity.ok(userService.toDTO(staff));
        }
        return ResponseEntity.status(403).build(); // not staff
    }

    // ✅ Update staff location (MORNING/EVENING tripType সহ)
    @PostMapping("/staff/location")
    public ResponseEntity<Void> updateStaffLocation(@RequestBody Location location) {
        User staff = userService.getCurrentUser();
        if (staff != null && staff.getRole() == Role.STAFF) {
            // tripType অবশ্যই MORNING বা EVENING হতে হবে
            userService.updateStaffLocation(staff, location, location.getTripType());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }

    // ✅ Update bus status (Active / Inactive / Maintenance)
    @PutMapping("/staff/bus/status")
    public ResponseEntity<Void> updateBusStatus(@RequestBody String status) {
        User staff = userService.getCurrentUser();
        if (staff != null && staff.getRole() == Role.STAFF && staff.getBusAssigned() != null) {
            userService.updateBusStatus(staff.getBusAssigned(), status);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }
}
