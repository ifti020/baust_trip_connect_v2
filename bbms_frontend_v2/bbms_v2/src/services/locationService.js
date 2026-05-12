// src/services/locationService.js
import axios from 'axios'

// ✅ Base API URL
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

// ✅ Helper → Staff token header
const staffAuthHeader = () => {
    const token = localStorage.getItem('staffToken')
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ✅ Helper → User/Admin token header
const userAuthHeader = () => {
    const token = localStorage.getItem('userToken') || localStorage.getItem('adminToken')
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ---------------- LOCATION SERVICE ----------------

// ✅ Fetch all staff locations (Admin/SuperAdmin → StaffLiveMap)
export const fetchStaffLocations = async () => {
    try {
        const response = await axios.get(`${API_URL}/locations/staff`, userAuthHeader())
        return response.data // List<LocationDTO>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch staff locations'
    }
}

// ✅ Get locations by bus (User/Staff/Admin/SuperAdmin → live tracking)
export const getLocationsByBus = async (busId) => {
    try {
        const response = await axios.get(`${API_URL}/locations/bus/${busId}`, userAuthHeader())
        return response.data // List<LocationDTO>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch bus location'
    }
}

// ✅ NEW: Get latest bus location by tripType (MORNING/EVENING)
export const getLatestLocationByTripType = async (busId, tripType) => {
    try {
        const response = await axios.get(`${API_URL}/locations/bus/${busId}/trip/${tripType}`, userAuthHeader())
        return response.data // LocationDTO with tripType
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch latest location by tripType'
    }
}

// ✅ Add new location (Staff → share location)
export const addLocation = async (locationData) => {
    try {
        const response = await axios.post(`${API_URL}/locations`, locationData, staffAuthHeader())
        return response.data // LocationDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to add location'
    }
}

// ✅ Update location (Staff/Admin/SuperAdmin)
export const updateLocation = async (id, locationData) => {
    try {
        const response = await axios.put(`${API_URL}/locations/${id}`, locationData, staffAuthHeader())
        return response.data // LocationDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update location'
    }
}

// ✅ Delete location (Admin/SuperAdmin only)
export const deleteLocation = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/locations/${id}`, userAuthHeader())
        return { success: true, data: response.data }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to delete location'
    }
}
