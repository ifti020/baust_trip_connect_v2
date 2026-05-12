<template>
  <div class="w-full h-screen relative">
    <!-- ✅ Map Container -->
    <div id="map" class="w-full h-full rounded-lg shadow-lg"></div>

    <!-- ✅ Floating Controls -->
    <div
        class="absolute top-4 left-4 bg-white dark:bg-gray-800 p-3 rounded-lg shadow-md flex flex-col space-y-2"
    >
      <!-- এখানে zoom-in.svg বসাও -->
       <img src="../assets/zoom-in.svg" alt="Zoom In" class="h-5 w-5 cursor-pointer" @click="zoomIn" />
      <!-- এখানে zoom-out.svg বসাও -->
       <img src="../assets/zoom-out.svg" alt="Zoom Out" class="h-5 w-5 cursor-pointer" @click="zoomOut" />
      <!-- এখানে locate.svg বসাও -->
       <img src="../assets/locate.svg" alt="Locate" class="h-5 w-5 cursor-pointer" @click="centerOnUser" />
    </div>

    <!-- ✅ Bottom Sheet (Bus Info) -->
    <div
        v-if="selectedBus"
        class="absolute bottom-0 left-0 w-full bg-white dark:bg-gray-900 rounded-t-xl shadow-lg p-4"
    >
      <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
        Bus {{ selectedBus.busNumber }}
      </h3>
      <p class="text-sm text-gray-700 dark:text-gray-300">
        Route: {{ selectedBus.routeName }}
      </p>
      <p class="text-sm text-gray-700 dark:text-gray-300">
        Status: {{ selectedBus.status }}
      </p>
      <button
          @click="closeSheet"
          class="mt-2 px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 hover:bg-gray-300 dark:hover:bg-gray-600 text-sm font-medium"
      >
        Close
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

// ✅ State
const map = ref(null)
const markers = ref([])
const selectedBus = ref(null)

// ✅ Initialize Map
onMounted(() => {
  map.value = L.map('map').setView([23.8103, 90.4125], 13) // Dhaka default

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors',
  }).addTo(map.value)

  // Example marker (replace with API data)
  const marker = L.marker([23.8103, 90.4125]).addTo(map.value)
  marker.bindPopup('Bus #101 - Mirpur Route')
  markers.value.push(marker)

  marker.on('click', () => {
    selectedBus.value = {
      busNumber: '101',
      routeName: 'Mirpur → BAUST',
      status: 'Active',
    }
  })
})

// ✅ Controls
const zoomIn = () => {
  map.value.zoomIn()
}
const zoomOut = () => {
  map.value.zoomOut()
}
const centerOnUser = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((pos) => {
      const { latitude, longitude } = pos.coords
      map.value.setView([latitude, longitude], 15)
    })
  }
}
const closeSheet = () => {
  selectedBus.value = null
}
</script>

<style scoped>
/* Uber/Google Maps style → smooth rounded map container */
#map {
  @apply rounded-lg;
}
</style>
