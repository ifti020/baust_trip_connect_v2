package com.baust.baust_trip_connect_v2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theme;          // light / dark
    private boolean notifications; // true / false
    private String language;       // en / bn

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public boolean isNotifications() { return notifications; }
    public void setNotifications(boolean notifications) { this.notifications = notifications; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
