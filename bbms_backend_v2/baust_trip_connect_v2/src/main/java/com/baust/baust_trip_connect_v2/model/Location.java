package com.baust.baust_trip_connect_v2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bus_locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Bus reference
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    // ✅ Staff/User reference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double latitude;
    private double longitude;
    private double speed;
    private LocalDateTime timestamp;

    // ✅ নতুন field → Trip type (MORNING / EVENING)
    private String tripType;

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getTripType() { return tripType; }
    public void setTripType(String tripType) { this.tripType = tripType; }
}
