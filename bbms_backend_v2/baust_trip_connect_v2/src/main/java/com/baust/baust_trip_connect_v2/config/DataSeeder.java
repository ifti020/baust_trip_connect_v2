package com.baust.baust_trip_connect_v2.config;

import com.baust.baust_trip_connect_v2.model.Role;
import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            // যদি কোনো user না থাকে, তাহলে default Admin তৈরি করবে
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setName("Default Admin");
                admin.setEmail("admin@baust.edu.bd");
                admin.setPassword(encoder.encode("admin123")); // BCrypt encode
                admin.setRole(Role.ADMIN); // শুধু Admin role
                userRepository.save(admin);

                System.out.println("✅ Default Admin created: admin@baust.edu.bd / admin123");
            }
        };
    }
}
