package com.baust.baust_trip_connect_v2.repository;

import com.baust.baust_trip_connect_v2.model.User;
import com.baust.baust_trip_connect_v2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // ✅ নতুন method → role অনুযায়ী সব user বের করবে
    List<User> findAllByRole(Role role);
}
