package com.baust.baust_trip_connect_v2.service;

import com.baust.baust_trip_connect_v2.model.Setting;
import com.baust.baust_trip_connect_v2.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public Setting getSettings() {
        return settingRepository.findAll().stream().findFirst().orElse(null);
    }

    public Setting updateSettings(Setting newSettings) {
        Setting existing = getSettings();
        if (existing == null) {
            return settingRepository.save(newSettings);
        }
        existing.setTheme(newSettings.getTheme());
        existing.setNotifications(newSettings.isNotifications());
        existing.setLanguage(newSettings.getLanguage());
        return settingRepository.save(existing);
    }
}
