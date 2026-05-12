// src/services/authService.js
import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// ✅ Register new user
export const register = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/auth/register`, userData);
        return response.data; // UserDTO
    } catch (error) {
        throw error.response?.data || 'Registration failed';
    }
};

// ✅ Login user
export const login = async (credentials) => {
    try {
        const response = await axios.post(`${API_URL}/auth/login`, credentials);
        const data = response.data; // AuthResponseDTO (token + user info)

        // ✅ Save JWT token in localStorage by role
        if (data.token) {
            if (data.role === "STAFF") {
                localStorage.setItem("staffToken", data.token);
            } else if (data.role === "USER") {
                localStorage.setItem("userToken", data.token);
            } else if (data.role === "ADMIN" || data.role === "SUPER_ADMIN") {
                localStorage.setItem("adminToken", data.token);
            }

            // Optional: save extra info
            localStorage.setItem("userRole", data.role);
            localStorage.setItem("userEmail", data.email);
        }

        return data;
    } catch (error) {
        throw error.response?.data || 'Login failed';
    }
};

// ✅ Logout user (clear all tokens)
export const logout = () => {
    localStorage.removeItem("staffToken");
    localStorage.removeItem("userToken");
    localStorage.removeItem("adminToken");
    localStorage.removeItem("userRole");
    localStorage.removeItem("userEmail");
};
