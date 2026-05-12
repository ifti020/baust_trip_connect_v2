// src/services/adminService.js
import axios from 'axios'

const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'
const ADMIN_URL = `${BASE_URL}/admin`
const USER_URL = `${BASE_URL}/users`
const BUS_URL = `${BASE_URL}/buses`

// ✅ Helper → Admin token header
const adminAuthHeader = () => {
    const token = localStorage.getItem('adminToken')
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ---------------- ADMIN SERVICE ----------------

// ✅ Assign staff role to a user
export const assignStaff = async (userId) => {
    try {
        const response = await axios.put(`${ADMIN_URL}/assign-staff/${userId}`, null, adminAuthHeader())
        return response.data
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to assign staff'
    }
}

// ✅ Remove staff role from a user
export const removeStaff = async (userId) => {
    try {
        const response = await axios.put(`${ADMIN_URL}/remove-staff/${userId}`, null, adminAuthHeader())
        return response.data
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to remove staff'
    }
}

// ✅ Assign bus to staff
export const assignBus = async (staffId, busId) => {
    try {
        const response = await axios.put(`${ADMIN_URL}/assign-bus/${staffId}/${busId}`, null, adminAuthHeader())
        return response.data
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to assign bus'
    }
}

// ✅ Update bus status (Active/Inactive/Maintenance)
export const updateBusStatus = async (busId, status) => {
    try {
        const response = await axios.put(`${ADMIN_URL}/buses/${busId}/status?status=${status}`, null, adminAuthHeader())
        return response.data
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update bus status'
    }
}

// ✅ Add new bus (Admin only)
export const addBus = async (busData) => {
    try {
        const response = await axios.post(BUS_URL, busData, adminAuthHeader())
        return response.data // BusDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to add bus'
    }
}

// ✅ Get all users
export const getAllUsers = async () => {
    try {
        const response = await axios.get(USER_URL, adminAuthHeader())
        return response.data // List<UserDTO>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch users'
    }
}

// ✅ Get user by ID
export const getUserById = async (id) => {
    try {
        const response = await axios.get(`${USER_URL}/${id}`, adminAuthHeader())
        return response.data // UserDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch user'
    }
}

// ✅ Update user
export const updateUser = async (id, userData) => {
    try {
        const response = await axios.put(`${USER_URL}/${id}`, userData, adminAuthHeader())
        return response.data // UserDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update user'
    }
}

// ✅ Delete user
export const deleteUser = async (id) => {
    try {
        const response = await axios.delete(`${USER_URL}/${id}`, adminAuthHeader())
        return { success: true, data: response.data }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to delete user'
    }
}
