package com.baust.baust_trip_connect_v2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buses")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // শুধু bus number থাকবে (যেমন: 1, 2, 3)
    private String busNumber;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    // ✅ Bus status field (Active / Inactive / Maintenance)
    private String status;

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ✅ Helper method → Dynamic bus name
    public String getDisplayName() {
        if (route != null && route.getRouteName() != null && busNumber != null) {
            return route.getRouteName() + " Bus " + busNumber;
        }
        return "Bus " + busNumber;
    }
}
