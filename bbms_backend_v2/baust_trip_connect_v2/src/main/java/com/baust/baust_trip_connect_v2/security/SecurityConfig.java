package com.baust.baust_trip_connect_v2.config;

import com.baust.baust_trip_connect_v2.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // ✅ Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ Security filter chain configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/routes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/buses/**").permitAll()

                        // Staff endpoints
                        .requestMatchers("/api/users/staff/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.POST, "/api/locations/**").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/locations/**").hasRole("STAFF")

                        // Admin/SuperAdmin endpoints
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("ADMIN","SUPER_ADMIN")

                        // ROUTES
                        .requestMatchers(HttpMethod.POST, "/api/routes/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/routes/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/routes/**").hasAnyRole("ADMIN","SUPER_ADMIN")

                        // BUSES
                        .requestMatchers(HttpMethod.POST, "/api/buses/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/buses/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/buses/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/buses/*/status").hasAnyRole("ADMIN","SUPER_ADMIN")

                        // LOCATIONS
                        .requestMatchers(HttpMethod.GET, "/api/locations/**").hasAnyRole("USER","STAFF","ADMIN","SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/locations/**").hasAnyRole("ADMIN","SUPER_ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
