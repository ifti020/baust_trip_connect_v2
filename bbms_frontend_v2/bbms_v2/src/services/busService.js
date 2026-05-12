// src/services/busService.js
import axios from 'axios'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

// ✅ Helper → Admin/User token header
const busAuthHeader = () => {
    const token = localStorage.getItem('adminToken') || localStorage.getItem('userToken')
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ---------------- BUS SERVICE ----------------

// ✅ Add new bus (Admin/SuperAdmin only)
export const addBus = async (busData) => {
    try {
        const response = await axios.post(`${API_URL}/buses`, busData, busAuthHeader())
        return response.data   // BusDTO → { id, displayName, routeName, status }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to add bus'
    }
}

// ✅ Get all buses (User, Staff, Admin, SuperAdmin)
export const getAllBuses = async () => {
    try {
        const response = await axios.get(`${API_URL}/buses`, busAuthHeader())
        return response.data   // List<BusDTO>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch buses'
    }
}

// ✅ Get bus by ID
export const getBusById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/buses/${id}`, busAuthHeader())
        return response.data   // BusDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Bus not found'
    }
}

// ✅ Update bus (Admin/SuperAdmin only)
export const updateBus = async (id, busData) => {
    try {
        const response = await axios.put(`${API_URL}/buses/${id}`, busData, busAuthHeader())
        return response.data   // BusDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update bus'
    }
}

// ✅ Delete bus (Admin/SuperAdmin only)
export const deleteBus = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/buses/${id}`, busAuthHeader())
        return { success: true, data: response.data }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to delete bus'
    }
}

// ✅ Update bus status (Admin/SuperAdmin only)
export const updateBusStatus = async (id, status) => {
    try {
        const response = await axios.put(`${API_URL}/buses/${id}/status`, status, busAuthHeader())
        return response.data   // BusDTO with updated status
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update bus status'
    }
}

// ✅ Get all bus display names (Dynamic names for User Flow table)
export const getAllBusDisplayNames = async () => {
    try {
        const response = await axios.get(`${API_URL}/buses/display-names`, busAuthHeader())
        return response.data   // List<String>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch bus display names'
    }
}
