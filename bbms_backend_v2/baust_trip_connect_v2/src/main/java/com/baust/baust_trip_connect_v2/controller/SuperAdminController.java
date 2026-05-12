package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    @Autowired
    private UserService userService;

    // ✅ Get all admins
    @GetMapping("/admins")
    public ResponseEntity<List<UserDTO>> getAllAdmins() {
        List<UserDTO> admins = userService.getAllUsers()
                .stream()
                .filter(u -> u.getRole() == Role.ADMIN)
                .toList();
        return ResponseEntity.ok(admins);
    }

    // ✅ Create new admin
    @PostMapping("/admins")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody User user) {
        user.setRole(Role.ADMIN);
        User saved = userService.saveUser(user);
        return ResponseEntity.ok(userService.toDTO(saved));
    }

    // ✅ Delete admin
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ System stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getSystemStats() {
        long users = userService.getAllUsers().size();
        long staff = userService.getAllUsers().stream().filter(u -> u.getRole() == Role.STAFF).count();
        long admins = userService.getAllUsers().stream().filter(u -> u.getRole() == Role.ADMIN).count();
        long superAdmins = userService.getAllUsers().stream().filter(u -> u.getRole() == Role.SUPER_ADMIN).count();

        // এখানে চাইলে busService, routeService থেকেও count আনতে পারো
        Map<String, Long> stats = Map.of(
                "users", users,
                "staff", staff,
                "admins", admins,
                "superAdmins", superAdmins
        );
        return ResponseEntity.ok(stats);
    }
}
