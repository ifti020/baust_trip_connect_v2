// src/services/routeService.js
import axios from 'axios'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

// ✅ Helper → Admin/User token header
const routeAuthHeader = () => {
    const token = localStorage.getItem('adminToken') || localStorage.getItem('userToken')
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ---------------- ROUTE SERVICE ----------------

// ✅ Add new route (Admin/SuperAdmin)
export const addRoute = async (routeData) => {
    try {
        const response = await axios.post(`${API_URL}/routes`, routeData, routeAuthHeader())
        return response.data   // RouteDTO → { id, routeName, morningDeparture, morningArrival, afternoonDeparture, afternoonArrival }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to add route'
    }
}

// ✅ Get all routes (User/Admin/SuperAdmin)
export const getAllRoutes = async () => {
    try {
        const response = await axios.get(`${API_URL}/routes`, routeAuthHeader())
        return response.data   // List<RouteDTO>
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch routes'
    }
}

// ✅ Get route by ID (User/Admin/SuperAdmin)
export const getRouteById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/routes/${id}`, routeAuthHeader())
        return response.data   // RouteDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Route not found'
    }
}

// ✅ Update route (Admin/SuperAdmin)
export const updateRoute = async (id, routeData) => {
    try {
        const response = await axios.put(`${API_URL}/routes/${id}`, routeData, routeAuthHeader())
        return response.data   // RouteDTO
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to update route'
    }
}

// ✅ Delete route (Admin/SuperAdmin only)
export const deleteRoute = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/routes/${id}`, routeAuthHeader())
        return { success: true, data: response.data }
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to delete route'
    }
}

// ✅ NEW: Get route schedule (Morning/Evening times)
export const getRouteSchedule = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/routes/${id}/schedule`, routeAuthHeader())
        return response.data   // RouteDTO with schedule fields
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch route schedule'
    }
}
