// src/services/userService.js
import axios from 'axios'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

// ✅ Helper → User/Admin token header
const userAuthHeader = () => {
    const token = localStorage.getItem("userToken") || localStorage.getItem("adminToken")
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ✅ Helper → Staff token header
const staffAuthHeader = () => {
    const token = localStorage.getItem("staffToken")
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ---------------- USER SERVICE ----------------

// ✅ Register new user → return UserDTO
export const registerUser = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/users/register`, userData)
        return response.data // UserDTO → { id, name, email, role, busId, busDisplayName, routeName }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to register user'
    }
}

// ✅ Get all users (Admin/SuperAdmin handled in adminService.js)
export const getAllUsers = async () => {
    try {
        const response = await axios.get(`${API_URL}/users`, userAuthHeader())
        return response.data // List<UserDTO>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch users'
    }
}

// ✅ Get user by ID
export const getUserById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/users/${id}`, userAuthHeader())
        return response.data // UserDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch user'
    }
}

// ✅ Update user
export const updateUser = async (id, userData) => {
    try {
        const response = await axios.put(`${API_URL}/users/${id}`, userData, userAuthHeader())
        return response.data // UserDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update user'
    }
}

// ✅ Delete user
export const deleteUser = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/users/${id}`, userAuthHeader())
        return { success: true, data: response.data }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to delete user'
    }
}

// ✅ Get staff profile (role check)
export const getStaffProfile = async () => {
    try {
        const response = await axios.get(`${API_URL}/users/staff/profile`, staffAuthHeader())
        return response.data // UserDTO with busDisplayName
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch staff profile'
    }
}

// ✅ Update staff profile
export const updateStaffProfile = async (data) => {
    try {
        const response = await axios.put(`${API_URL}/users/staff/profile`, data, staffAuthHeader())
        return response.data // UserDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update staff profile'
    }
}

// ✅ Staff update location (tripType → MORNING/EVENING)
export const updateStaffLocation = async (locationData) => {
    try {
        const response = await axios.post(`${API_URL}/users/staff/location`, locationData, staffAuthHeader())
        return response.data // LocationDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update staff location'
    }
}

// ✅ Staff update bus status
export const updateStaffBusStatus = async (status) => {
    try {
        const response = await axios.put(`${API_URL}/users/staff/bus/status`, status, staffAuthHeader())
        return response.data
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update bus status'
    }
}
