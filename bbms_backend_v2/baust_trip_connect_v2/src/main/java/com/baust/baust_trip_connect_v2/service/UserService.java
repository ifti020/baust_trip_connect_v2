package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.model.Bus;
import com.baust.baust_trip_connect_v2.model.Location;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.repository.BusRepository;
import com.baust.baust_trip_connect_v2.repository.LocationRepository;
import com.baust.baust_trip_connect_v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BusRepository busRepository;

    // ✅ Save user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ✅ Mapper: Entity → DTO (Dynamic bus name সহ)
    public UserDTO toDTO(User user) {
        Long busId = (user.getBusAssigned() != null) ? user.getBusAssigned().getId() : null;
        String busNumber = (user.getBusAssigned() != null) ? user.getBusAssigned().getBusNumber() : null;
        String busDisplayName = (user.getBusAssigned() != null) ? user.getBusAssigned().getDisplayName() : null;
        String routeName = (user.getBusAssigned() != null && user.getBusAssigned().getRoute() != null)
                ? user.getBusAssigned().getRoute().getRouteName()
                : null;

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                busId,
                busNumber,
                routeName,
                busDisplayName
        );
    }

    // ✅ Get all users → DTO list
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // ✅ Update user
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        user.setBusAssigned(userDetails.getBusAssigned()); // ✅ ensure bus assignment persists
        return userRepository.save(user);
    }

    // ✅ Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ✅ Get current authenticated user
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String email = authentication.getName();
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    // ✅ Update staff location (with MORNING/EVENING tripType)
    public void updateStaffLocation(User staff, Location location, String tripType) {
        location.setUser(staff);
        location.setTripType(tripType); // MORNING / EVENING
        locationRepository.save(location);
    }

    // ✅ Update bus status (Active / Inactive / Maintenance)
    public void updateBusStatus(Bus bus, String status) {
        bus.setStatus(status);
        busRepository.save(bus);
    }

    // ✅ Get all buses with dynamic names (for User Flow table)
    public List<String> getAllBusDisplayNames() {
        return busRepository.findAll()
                .stream()
                .map(Bus::getDisplayName)
                .collect(Collectors.toList());
    }
}
