package com.baust.baust_trip_connect_v2.repository;

import com.baust.baust_trip_connect_v2.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    // ✅ নির্দিষ্ট bus এর সব location history
    List<Location> findByBusId(Long busId);

    // ✅ নির্দিষ্ট bus এর recent 50 updates (latest first)
    List<Location> findTop50ByBusIdOrderByTimestampDesc(Long busId);

    // ✅ নির্দিষ্ট staff এর সর্বশেষ location
    Location findTop1ByUserIdOrderByTimestampDesc(Long userId);

    // 🔹 NEW: নির্দিষ্ট bus + tripType অনুযায়ী সর্বশেষ location (MORNING/EVENING)
    Location findTop1ByBusIdAndTripTypeOrderByTimestampDesc(Long busId, String tripType);
}
