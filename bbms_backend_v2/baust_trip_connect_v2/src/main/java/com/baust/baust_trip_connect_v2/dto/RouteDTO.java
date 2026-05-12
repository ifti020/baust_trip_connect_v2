package com.baust.baust_trip_connect_v2.dto;

public class RouteDTO {
    private Long id;
    private String routeName;

    // 🔹 NEW: Fixed schedule fields
    private String morningDeparture;
    private String morningArrival;
    private String afternoonDeparture;
    private String afternoonArrival;

    // ✅ Constructor
    public RouteDTO(Long id, String routeName,
                    String morningDeparture, String morningArrival,
                    String afternoonDeparture, String afternoonArrival) {
        this.id = id;
        this.routeName = routeName;
        this.morningDeparture = morningDeparture;
        this.morningArrival = morningArrival;
        this.afternoonDeparture = afternoonDeparture;
        this.afternoonArrival = afternoonArrival;
    }

    // ✅ Getters
    public Long getId() { return id; }
    public String getRouteName() { return routeName; }
    public String getMorningDeparture() { return morningDeparture; }
    public String getMorningArrival() { return morningArrival; }
    public String getAfternoonDeparture() { return afternoonDeparture; }
    public String getAfternoonArrival() { return afternoonArrival; }

    // ✅ Setters (optional, for future use)
    public void setId(Long id) { this.id = id; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public void setMorningDeparture(String morningDeparture) { this.morningDeparture = morningDeparture; }
    public void setMorningArrival(String morningArrival) { this.morningArrival = morningArrival; }
    public void setAfternoonDeparture(String afternoonDeparture) { this.afternoonDeparture = afternoonDeparture; }
    public void setAfternoonArrival(String afternoonArrival) { this.afternoonArrival = afternoonArrival; }
}
