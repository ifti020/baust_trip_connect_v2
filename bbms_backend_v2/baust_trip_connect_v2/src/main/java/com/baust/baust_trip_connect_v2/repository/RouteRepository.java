package com.baust.baust_trip_connect_v2.repository;

import com.baust.baust_trip_connect_v2.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByRouteName(String routeName);
}
