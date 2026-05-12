import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css'

// CSS imports
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'leaflet/dist/leaflet.css'
import '@fortawesome/fontawesome-free/css/all.min.css'

// ✅ Axios interceptor setup
import axios from 'axios'

axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
}, error => {
    return Promise.reject(error)
})

// ✅ Optional: router guard (protect admin routes)
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('userRole')

    if (to.path.startsWith('/admin')) {
        if (!token || role !== 'ADMIN') {
            return next('/login') // redirect if not admin
        }
    }
    next()
})

createApp(App)
    .use(router)
    .mount('#app')
