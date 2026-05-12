// src/services/superAdminService.js
import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// ✅ Get all admins
export const getAllAdmins = async () => {
    try {
        const response = await axios.get(`${API_URL}/admins`);
        return response.data; // List<UserDTO with role=ADMIN
    } catch (error) {
        throw error.response?.data || 'Failed to fetch admins';
    }
};

// ✅ Create new admin
export const createNewAdmin = async (adminData) => {
    try {
        const response = await axios.post(`${API_URL}/admins`, { ...adminData, role: 'ADMIN' });
        return response.data; // UserDTO
    } catch (error) {
        throw error.response?.data || 'Failed to create admin';
    }
};

// ✅ Delete admin
export const deleteAdminById = async (id) => {
    try {
        await axios.delete(`${API_URL}/admins/${id}`);
        return true;
    } catch (error) {
        throw error.response?.data || 'Failed to delete admin';
    }
};

// ✅ System stats
export const getSystemStats = async () => {
    try {
        const response = await axios.get(`${API_URL}/system/stats`);
        return response.data; // { users, staff, buses, routes }
    } catch (error) {
        throw error.response?.data || 'Failed to fetch system stats';
    }
};
