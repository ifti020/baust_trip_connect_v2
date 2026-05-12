// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'

// ✅ Views import
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Dashboard from '../views/Dashboard.vue'
import BusList from '../views/BusList.vue'
import BusDetails from '../views/BusDetails.vue'
import RouteList from '../views/RouteList.vue'
import RouteDetails from '../views/RouteDetails.vue'
import LocationTracker from '../views/LocationTracker.vue'
import UserDashboard from '../views/UserDashboard.vue'
import UserProfile from '../views/UserProfile.vue'
import UserManagement from '../views/UserManagement.vue'
import StaffDashboard from '../views/StaffDashboard.vue'
import StaffLiveMap from '../views/StaffLiveMap.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import SuperAdminDashboard from '../views/SuperAdminDashboard.vue'
import Analytics from '../views/Analytics.vue'
import Reports from '../views/Reports.vue'
import Notifications from '../views/Notifications.vue'
import Payments from '../views/Payments.vue'
import Settings from '../views/Settings.vue'
import HelpCenter from '../views/HelpCenter.vue'
import GuideDetails from '../views/GuideDetails.vue'

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    { path: '/dashboard', name: 'Dashboard', component: Dashboard },

    // ✅ User
    { path: '/user', name: 'UserDashboard', component: UserDashboard },
    { path: '/user/profile', name: 'UserProfile', component: UserProfile },
    { path: '/user/manage', name: 'UserManagement', component: UserManagement },

    // ✅ Staff
    { path: '/staff', name: 'StaffDashboard', component: StaffDashboard },
    { path: '/staff/map', name: 'StaffLiveMap', component: StaffLiveMap },

    // ✅ Admin
    { path: '/admin', name: 'AdminDashboard', component: AdminDashboard },

    // ✅ Super Admin
    { path: '/superadmin', name: 'SuperAdminDashboard', component: SuperAdminDashboard },

    // ✅ Bus & Route
    { path: '/buses', name: 'BusList', component: BusList },
    { path: '/buses/:id', name: 'BusDetails', component: BusDetails },
    { path: '/routes', name: 'RouteList', component: RouteList },
    { path: '/routes/:id', name: 'RouteDetails', component: RouteDetails },

    // ✅ Location
    { path: '/locations', name: 'LocationTracker', component: LocationTracker },

    // ✅ Extra Features
    { path: '/analytics', name: 'Analytics', component: Analytics },
    { path: '/reports', name: 'Reports', component: Reports },
    { path: '/notifications', name: 'Notifications', component: Notifications },
    { path: '/payments', name: 'Payments', component: Payments },
    { path: '/settings', name: 'Settings', component: Settings },
    { path: '/help', name: 'HelpCenter', component: HelpCenter },
    { path: '/guide/:id', name: 'GuideDetails', component: GuideDetails },

    { path: '/users/:id', component: UserProfile },

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// ✅ Simple auth guard
router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register']
    const authRequired = !publicPages.includes(to.path)
    const loggedIn = localStorage.getItem('token')

    if (authRequired && !loggedIn) {
        return next('/login')
    }
    next()
})

export default router
