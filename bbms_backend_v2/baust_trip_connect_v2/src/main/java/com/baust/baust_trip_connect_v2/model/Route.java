package com.baust.baust_trip_connect_v2.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeName;

    // ✅ Morning schedule
    private LocalTime morningDeparture;
    private LocalTime morningArrival;

    // ✅ Afternoon schedule
    private LocalTime afternoonDeparture;
    private LocalTime afternoonArrival;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public LocalTime getMorningDeparture() { return morningDeparture; }
    public void setMorningDeparture(LocalTime morningDeparture) { this.morningDeparture = morningDeparture; }

    public LocalTime getMorningArrival() { return morningArrival; }
    public void setMorningArrival(LocalTime morningArrival) { this.morningArrival = morningArrival; }

    public LocalTime getAfternoonDeparture() { return afternoonDeparture; }
    public void setAfternoonDeparture(LocalTime afternoonDeparture) { this.afternoonDeparture = afternoonDeparture; }

    public LocalTime getAfternoonArrival() { return afternoonArrival; }
    public void setAfternoonArrival(LocalTime afternoonArrival) { this.afternoonArrival = afternoonArrival; }
}
