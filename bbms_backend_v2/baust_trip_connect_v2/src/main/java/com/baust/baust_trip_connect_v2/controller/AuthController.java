package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.dto.AuthResponseDTO;
import com.baust.baust_trip_connect_v2.dto.LoginRequest;
import com.baust.baust_trip_connect_v2.dto.UserDTO;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.service.AuthService;
import com.baust.baust_trip_connect_v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // ✅ Register endpoint → return UserDTO
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        User savedUser = authService.register(user);
        return ResponseEntity.ok(userService.toDTO(savedUser));
    }

    // ✅ Login endpoint → return AuthResponseDTO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponseDTO response = authService.login(request.getEmail(), request.getPassword());
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
