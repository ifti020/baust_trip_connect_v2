// src/services/userDashboardService.js
import axios from 'axios'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

// ✅ Helper → User/Admin token header
const userAuthHeader = () => {
    const token = localStorage.getItem('userToken') || localStorage.getItem('adminToken')
    return token ? { headers: { Authorization: `Bearer ${token}` } } : {}
}

// ---------------- USER DASHBOARD SERVICE ----------------

// ✅ Get current logged-in user profile
export const getUserProfile = async () => {
    try {
        const response = await axios.get(`${API_URL}/user/profile`, userAuthHeader())
        return response.data
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch user profile'
    }
}

// ✅ Get dashboard cards (custom logic, can be extended)
export const getUserDashboardCards = async () => {
    try {
        const routesRes = await axios.get(`${API_URL}/routes`, userAuthHeader())
        const busesRes = await axios.get(`${API_URL}/buses`, userAuthHeader())

        return [
            {
                id: 1,
                title: 'Available Routes',
                description: 'Total routes you can travel',
                link: '/routes',
                value: routesRes.data.length
            },
            {
                id: 2,
                title: 'Available Buses',
                description: 'Total buses currently active',
                link: '/buses',
                value: busesRes.data.length
            },
            {
                id: 3,
                title: 'Track My Bus',
                description: 'See live location of your assigned bus',
                link: '/dashboard',
                value: 0 // dynamic value handled in UserDashboard.vue
            }
        ]
    } catch (error) {
        throw error.response?.data?.message || error.message || 'Failed to fetch dashboard cards'
    }
}
