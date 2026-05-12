package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.model.Bus;
import com.baust.baust_trip_connect_v2.repository.UserRepository;
import com.baust.baust_trip_connect_v2.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;

    // ✅ Assign staff (USER → STAFF)
    public UserDTO assignStaff(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(Role.STAFF);
        userRepository.save(user);
        return toDTO(user);
    }

    // ✅ Remove staff (STAFF → USER)
    public UserDTO removeStaff(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(Role.USER);
        userRepository.save(user);
        return toDTO(user);
    }

    // ✅ Assign bus to staff (User.busAssigned আপডেট হবে)
    public UserDTO assignBus(Long staffId, Long busId) {
        User staff = userRepository.findById(staffId).orElseThrow();
        Bus bus = busRepository.findById(busId).orElseThrow();

        if (staff.getRole() != Role.STAFF) {
            throw new IllegalArgumentException("User " + staff.getEmail() + " is not a staff");
        }

        staff.setBusAssigned(bus);
        userRepository.save(staff);
        return toDTO(staff);
    }

    // ✅ Update bus status
    public void updateBusStatus(Long busId, String status) {
        Bus bus = busRepository.findById(busId).orElseThrow();
        bus.setStatus(status);
        busRepository.save(bus);
    }

    // ✅ Helper → convert User → UserDTO
    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getBusAssigned() != null ? user.getBusAssigned().getId() : null,        // busId
                user.getBusAssigned() != null ? user.getBusAssigned().getBusNumber() : null, // busNumber
                (user.getBusAssigned() != null && user.getBusAssigned().getRoute() != null)
                        ? user.getBusAssigned().getRoute().getRouteName()
                        : null,
                user.getBusAssigned() != null ? user.getBusAssigned().getDisplayName() : null // displayName
        );
    }
}
