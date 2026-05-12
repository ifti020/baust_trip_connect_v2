package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SuperAdminService {

    @Autowired
    private UserService userService;
    @Autowired
    private BusService busService;
    @Autowired
    private RouteService routeService;

    // ✅ Get all admins
    public List<UserDTO> getAllAdmins() {
        return userService.getAllUsers().stream()
                .filter(u -> u.getRole() == Role.ADMIN)
                .toList();
    }

    // ✅ Create new admin
    public UserDTO createAdmin(User user) {
        user.setRole(Role.ADMIN);
        User saved = userService.saveUser(user);
        return userService.toDTO(saved);
    }

    // ✅ Delete admin
    public void deleteAdmin(Long id) {
        userService.deleteUser(id);
    }

    // ✅ System stats
    public Map<String, Long> getSystemStats() {
        long users = userService.getAllUsers().size();
        long staff = userService.getAllUsers().stream().filter(u -> u.getRole() == Role.STAFF).count();
        long admins = userService.getAllUsers().stream().filter(u -> u.getRole() == Role.ADMIN).count();
        long buses = busService.getAllBuses().size();
        long routes = routeService.getAllRoutes().size();

        return Map.of(
                "users", users,
                "staff", staff,
                "admins", admins,
                "buses", buses,
                "routes", routes
        );
    }
}
