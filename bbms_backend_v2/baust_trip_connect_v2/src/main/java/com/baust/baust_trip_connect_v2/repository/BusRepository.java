package com.baust.baust_trip_connect_v2.repository;

import com.baust.baust_trip_connect_v2.model.Bus;
import com.baust.baust_trip_connect_v2.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByRoute(Route route);
}
