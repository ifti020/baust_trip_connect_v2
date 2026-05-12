package com.baust.baust_trip_connect_v2.controller;

import com.baust.baust_trip_connect_v2.model.Setting;
import com.baust.baust_trip_connect_v2.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingController {

    @Autowired
    private SettingService settingService;

    // ✅ Get settings
    @GetMapping
    public ResponseEntity<Setting> getSettings() {
        Setting settings = settingService.getSettings();
        return settings != null ? ResponseEntity.ok(settings) : ResponseEntity.notFound().build();
    }

    // ✅ Update settings
    @PutMapping
    public ResponseEntity<Setting> updateSettings(@RequestBody Setting settings) {
        return ResponseEntity.ok(settingService.updateSettings(settings));
    }
}
