<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        User Dashboard
      </h2>

      <!-- ✅ Shortcut Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <router-link
            v-for="card in cards"
            :key="card.id"
            :to="card.link"
            class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 hover:shadow-lg transition flex flex-col justify-between"
        >
          <h3 class="text-lg font-semibold text-indigo-600 dark:text-indigo-400 mb-2">
            {{ card.title }}
          </h3>
          <p class="text-sm text-gray-700 dark:text-gray-300 flex-1">
            {{ card.description }}
          </p>
        </router-link>
      </div>

      <!-- ✅ Live Bus Tracking -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 mt-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Track My Bus
        </h3>

        <!-- ✅ Map always rendered -->
        <div class="mt-4">
          <div id="map" class="w-full h-96 rounded-md"></div>
          <p v-if="locations.length">
            Showing {{ locations.length }} updates for Bus #{{ busId }}
          </p>
        </div>

        <p v-if="error" class="text-sm text-red-600 dark:text-red-400 mt-2">
          {{ error }}
        </p>
      </div>
    </main>

    <!-- ✅ Footer -->
<!--    <Footer />-->
  </div>
</template>

<script setup>
import Navbar from '../components/Navbar.vue'
import Footer from '../components/Footer.vue'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getUserDashboardCards } from '../services/userDashboardService'
import { getLocationsByBus } from '../services/locationService'
import L from 'leaflet'

const cards = ref([])
const error = ref(null)
const route = useRoute()
const busId = ref(route.query.busId || '')   // ✅ Query param থেকে busId আসবে
const locations = ref([])
let map = null
let markers = []
let intervalId = null

onMounted(async () => {
  try {
    cards.value = await getUserDashboardCards()
  } catch (err) {
    error.value = err || 'Failed to load dashboard'
  }

  // ✅ যদি busId query param এ থাকে, auto tracking শুরু হবে
  if (busId.value) {
    startTracking()
  }
})

// ✅ Start tracking bus (poll every 5s)
const startTracking = () => {
  if (!busId.value) {
    error.value = 'No Bus ID provided'
    return
  }
  fetchBusLocation(busId.value) // immediate call
  intervalId = setInterval(() => fetchBusLocation(busId.value), 5000) // repeat every 5s
}

// ✅ Fetch bus location
const fetchBusLocation = async (busIdParam) => {
  try {
    locations.value = await getLocationsByBus(busIdParam)

    if (!map) {
      map = L.map('map').setView([23.77, 90.37], 12)
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      }).addTo(map)
    }
    markers.forEach(m => map.removeLayer(m))
    markers = []
    locations.value.forEach(loc => {
      const marker = L.marker([loc.latitude, loc.longitude])
          .addTo(map)
          .bindPopup(`Bus: ${loc.busDisplayName}<br>Lat: ${loc.latitude}<br>Lng: ${loc.longitude}`)
      markers.push(marker)
    })
    if (locations.value.length > 0) {
      const last = locations.value[locations.value.length - 1]
      map.setView([last.latitude, last.longitude], 14)
    }
  } catch (err) {
    error.value = 'Failed to fetch bus location'
  }
}

// ✅ Stop tracking
const stopTracking = () => {
  if (intervalId) {
    clearInterval(intervalId)
    intervalId = null
  }
  if (map) {
    markers.forEach(m => map.removeLayer(m))
    markers = []
    locations.value = []
  }
}
</script>

<style scoped>
#map {
  height: 400px;
}
</style>
