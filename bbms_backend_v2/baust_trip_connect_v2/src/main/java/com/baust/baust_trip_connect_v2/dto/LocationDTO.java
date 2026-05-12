package com.baust.baust_trip_connect_v2.dto;

import java.time.LocalDateTime;

public class LocationDTO {
    private Long id;
    private Long busId;
    private String busDisplayName; // 🔹 NEW: Dynamic নাম (RouteName + BusNo)
    private String routeName;
    private double latitude;
    private double longitude;
    private double speed;
    private LocalDateTime timestamp;
    private String staffName;      // ✅ Staff এর নাম
    private String tripType;       // 🔹 NEW: MORNING / EVENING

    // ✅ Constructor
    public LocationDTO(Long id, Long busId, String busDisplayName, String routeName,
                       double latitude, double longitude, double speed,
                       LocalDateTime timestamp, String staffName, String tripType) {
        this.id = id;
        this.busId = busId;
        this.busDisplayName = busDisplayName;
        this.routeName = routeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.timestamp = timestamp;
        this.staffName = staffName;
        this.tripType = tripType;
    }

    // ✅ Getters
    public Long getId() { return id; }
    public Long getBusId() { return busId; }
    public String getBusDisplayName() { return busDisplayName; }
    public String getRouteName() { return routeName; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getSpeed() { return speed; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStaffName() { return staffName; }
    public String getTripType() { return tripType; }

    // ✅ Setters (optional future use)
    public void setId(Long id) { this.id = id; }
    public void setBusId(Long busId) { this.busId = busId; }
    public void setBusDisplayName(String busDisplayName) { this.busDisplayName = busDisplayName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setSpeed(double speed) { this.speed = speed; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public void setTripType(String tripType) { this.tripType = tripType; }
}
