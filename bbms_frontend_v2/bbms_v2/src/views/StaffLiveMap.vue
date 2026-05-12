<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Staff Live Map
      </h2>

      <!-- ✅ Controls -->
      <div class="flex space-x-3 mb-4">
        <button
            @click="startTracking"
            class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
        >
          Start Tracking
        </button>
        <button
            @click="stopTracking"
            class="px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 font-medium"
        >
          Stop
        </button>
      </div>

      <!-- ✅ Map -->
      <div id="staffMap" class="w-full h-[500px] rounded-lg shadow-lg"></div>

      <!-- ✅ Error -->
      <p v-if="error" class="text-red-500 text-sm">{{ error }}</p>
    </main>

    <!-- ✅ Footer -->
    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../components/Navbar.vue'
import Footer from '../components/Footer.vue'
import { ref, onMounted, onUnmounted } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { fetchStaffLocations } from '../services/locationService.js' // ✅ fixed import

const map = ref(null)
const markers = ref({})
const intervalId = ref(null)
const error = ref(null)

// ✅ Initialize Map
onMounted(() => {
  map.value = L.map('staffMap').setView([23.8103, 90.4125], 13) // Dhaka default
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors',
  }).addTo(map.value)
})

onUnmounted(() => {
  stopTracking()
  if (map.value) {
    map.value.remove()
    map.value = null
  }
})

// ✅ Fetch and render staff locations
const fetchLocations = async () => {
  try {
    const response = await fetchStaffLocations()
    if (response && response.length > 0) {
      response.forEach((staff) => {
        const latLng = [staff.latitude, staff.longitude]

        if (markers.value[staff.id]) {
          markers.value[staff.id]
              .setLatLng(latLng)
              .bindPopup(`Staff: ${staff.staffName} | Bus: ${staff.busNumber}`)
        } else {
          markers.value[staff.id] = L.marker(latLng).addTo(map.value)
              .bindPopup(`Staff: ${staff.staffName} | Bus: ${staff.busNumber}`)
        }
      })
    }
  } catch (err) {
    error.value = err || 'Failed to fetch staff locations'
  }
}

// ✅ Start/Stop tracking
const startTracking = () => {
  error.value = null
  fetchLocations()
  intervalId.value = setInterval(fetchLocations, 5000) // every 5s
}

const stopTracking = () => {
  if (intervalId.value) clearInterval(intervalId.value)
  intervalId.value = null
}
</script>

<style scoped>
#staffMap {
  border-radius: 0.5rem;
}
</style>
