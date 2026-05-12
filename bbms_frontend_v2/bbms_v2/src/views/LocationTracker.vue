<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <Navbar />

    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Live Bus Tracker
      </h2>

      <!-- ✅ Map -->
      <div id="trackerMap" class="w-full h-[500px] rounded-lg shadow-lg"></div>

      <!-- ✅ Error -->
      <p v-if="error" class="text-red-500 text-sm">{{ error }}</p>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../components/Navbar.vue'
import Footer from '../components/Footer.vue'
import { ref, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { getLocationsByBus } from '../services/locationService'

const route = useRoute()
const busId = ref(route.query.busId || '')   // ✅ Query param থেকে busId আসবে
const map = ref(null)
const marker = ref(null)
const intervalId = ref(null)
const error = ref(null)

// ✅ Initialize Map (default view)
onMounted(() => {
  map.value = L.map('trackerMap').setView([23.77, 90.37], 7) // Default Dhaka view
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors',
  }).addTo(map.value)

  if (busId.value) {
    fetchLocation()
    intervalId.value = setInterval(fetchLocation, 5000) // auto refresh every 5s
  }
})

onUnmounted(() => {
  stopTracking()
  if (map.value) {
    map.value.remove()
    map.value = null
  }
})

// ✅ Fetch and render bus location
const fetchLocation = async () => {
  try {
    const locations = await getLocationsByBus(busId.value)
    if (locations && locations.length > 0) {
      const latest = locations[locations.length - 1]
      const latLng = [latest.latitude, latest.longitude]

      const popupContent = `
        Bus: ${latest.busDisplayName}
        | Route: ${latest.routeName}
        | Speed: ${latest.speed} km/h
        | Staff: ${latest.staffName || 'N/A'}
      `

      if (marker.value) {
        marker.value.setLatLng(latLng)
        marker.value.setPopupContent(popupContent)
      } else {
        const busIcon = L.icon({
          iconUrl: new URL('../assets/bus-marker.png', import.meta.url).href,
          shadowUrl: new URL('leaflet/dist/images/marker-shadow.png', import.meta.url).href,
          iconSize: [40, 40],
          iconAnchor: [20, 40],
          popupAnchor: [0, -40],
          shadowSize: [41, 41]
        })
        marker.value = L.marker(latLng, { icon: busIcon }).addTo(map.value)
        marker.value.bindPopup(popupContent).openPopup()
      }

      map.value.setView(latLng, 15)
    }
  } catch (err) {
    error.value = err || 'Failed to fetch bus location'
  }
}

// ✅ Stop tracking
const stopTracking = () => {
  if (intervalId.value) clearInterval(intervalId.value)
  intervalId.value = null
}
</script>

<style scoped>
#trackerMap {
  @apply rounded-lg;
}
</style>
