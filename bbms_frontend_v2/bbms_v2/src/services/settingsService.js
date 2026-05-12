// src/services/settingsService.js
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

// ✅ Get settings
export const getSettings = async () => {
    try {
        const response = await axios.get(`${API_URL}/settings`, {
            headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
        });
        return response.data;
    } catch (error) {
        throw error.response?.data || "Failed to fetch settings";
    }
};

// ✅ Update theme
export const updateThemeSetting = async (theme) => {
    try {
        const response = await axios.put(`${API_URL}/settings`, { theme });
        return response.data;
    } catch (error) {
        throw error.response?.data || "Failed to update theme";
    }
};

// ✅ Update notifications
export const updateNotificationSetting = async (notifications) => {
    try {
        const response = await axios.put(`${API_URL}/settings`, { notifications });
        return response.data;
    } catch (error) {
        throw error.response?.data || "Failed to update notifications";
    }
};

// ✅ Update language
export const updateLanguageSetting = async (language) => {
    try {
        const response = await axios.put(`${API_URL}/settings`, { language });
        return response.data;
    } catch (error) {
        throw error.response?.data || "Failed to update language";
    }
};
