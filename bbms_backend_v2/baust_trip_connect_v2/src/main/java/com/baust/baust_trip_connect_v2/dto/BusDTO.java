package com.baust.baust_trip_connect_v2.dto;

public class BusDTO {
    private Long id;
    private String displayName; // 🔹 NEW: Dynamic নাম (RouteName + BusNo)
    private String routeName;
    private String status;      // 🔹 NEW: Bus status (Active/Inactive/Maintenance)

    // ✅ Constructor
    public BusDTO(Long id, String displayName, String routeName, String status) {
        this.id = id;
        this.displayName = displayName;
        this.routeName = routeName;
        this.status = status;
    }

    // ✅ Getters
    public Long getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getRouteName() { return routeName; }
    public String getStatus() { return status; }

    // ✅ Setters (optional, for future use)
    public void setId(Long id) { this.id = id; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public void setStatus(String status) { this.status = status; }
}
