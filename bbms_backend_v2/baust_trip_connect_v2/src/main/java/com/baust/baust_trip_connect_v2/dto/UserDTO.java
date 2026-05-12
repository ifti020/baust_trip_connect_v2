package com.baust.baust_trip_connect_v2.dto;

import com.baust.baust_trip_connect_v2.model.Role;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;   // Enum type 그대로 থাকবে

    // ✅ Bus assignment info
    private Long busId;             // Assigned bus primary key
    private String busNumber;       // শুধু bus number (যেমন: 1, 2, 3)
    private String routeName;       // Route name (e.g. Dinajpur)
    private String busDisplayName;  // Dynamic নাম (RouteName + BusNo)

    // ✅ Constructor
    public UserDTO(Long id, String name, String email, Role role,
                   Long busId, String busNumber, String routeName, String busDisplayName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.busId = busId;
        this.busNumber = busNumber;
        this.routeName = routeName;
        this.busDisplayName = busDisplayName;
    }

    // ✅ Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public Long getBusId() { return busId; }
    public String getBusNumber() { return busNumber; }
    public String getRouteName() { return routeName; }
    public String getBusDisplayName() { return busDisplayName; }

    // ✅ Setters (optional, for future use)
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(Role role) { this.role = role; }
    public void setBusId(Long busId) { this.busId = busId; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public void setBusDisplayName(String busDisplayName) { this.busDisplayName = busDisplayName; }
}
