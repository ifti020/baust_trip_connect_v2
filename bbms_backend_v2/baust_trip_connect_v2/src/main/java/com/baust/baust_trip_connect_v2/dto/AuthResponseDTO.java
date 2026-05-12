package com.baust.baust_trip_connect_v2.dto;

import com.baust.baust_trip_connect_v2.model.Role;

public class AuthResponseDTO {
    private String token;
    private Long id;
    private String name;
    private String email;
    private Role role;

    public AuthResponseDTO(String token, Long id, String name, String email, Role role) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}
