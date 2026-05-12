package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.dto.AuthResponseDTO;
import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.repository.UserRepository;
import com.baust.baust_trip_connect_v2.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Register user
    public User register(User user) {
        // যদি role না পাঠানো হয় → default USER assign করো
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // ✅ password hash করো
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ✅ Login user → return DTO
    public AuthResponseDTO login(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                // ✅ role null হলে safe check
                if (user.getRole() == null) {
                    throw new RuntimeException("User role not assigned!");
                }
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                return new AuthResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
            }
        }
        return null; // invalid credentials
    }
}


