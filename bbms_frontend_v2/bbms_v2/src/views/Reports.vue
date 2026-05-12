<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Reports & Analytics
      </h2>

      <!-- ✅ Staff Location History -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Staff Location History
        </h3>
        <table class="w-full text-sm text-left text-gray-700 dark:text-gray-300">
          <thead>
          <tr class="bg-gray-200 dark:bg-gray-700">
            <th class="px-3 py-2">Staff</th>
            <th class="px-3 py-2">Bus</th>
            <th class="px-3 py-2">Latitude</th>
            <th class="px-3 py-2">Longitude</th>
            <th class="px-3 py-2">Timestamp</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="loc in staffLocations" :key="loc.id" class="border-b dark:border-gray-600">
            <td class="px-3 py-2">{{ loc.staffName }}</td>
            <td class="px-3 py-2">Bus #{{ loc.busNumber }}</td>
            <td class="px-3 py-2">{{ loc.latitude }}</td>
            <td class="px-3 py-2">{{ loc.longitude }}</td>
            <td class="px-3 py-2">{{ loc.timestamp }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- ✅ Bus Usage -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Bus Usage
        </h3>
        <ul class="space-y-2">
          <li
              v-for="bus in busUsage"
              :key="bus.busId"
              class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2"
          >
            <span class="text-sm text-gray-800 dark:text-gray-200">
              Bus #{{ bus.busNumber }} (Route: {{ bus.routeName }})
            </span>
            <span class="text-sm font-semibold text-indigo-600 dark:text-indigo-400">
              Trips: {{ bus.trips }} | Passengers: {{ bus.passengers }}
            </span>
          </li>
        </ul>
      </div>

      <!-- ✅ Route Performance -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Route Performance
        </h3>
        <ul class="space-y-2">
          <li
              v-for="route in routePerformance"
              :key="route.routeId"
              class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2"
          >
            <span class="text-sm text-gray-800 dark:text-gray-200">
              {{ route.routeName }}
            </span>
            <span class="text-sm font-semibold text-indigo-600 dark:text-indigo-400">
              Avg Speed: {{ route.avgSpeed }} km/h | On-time %: {{ route.onTimePercentage }}%
            </span>
          </li>
        </ul>
      </div>
    </main>

    <!-- ✅ Footer -->
    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../components/Navbar.vue'
import Footer from '../components/Footer.vue'
import { ref, onMounted } from 'vue'
import { getStaffLocationHistory, getBusUsage, getRoutePerformance } from '../services/reportService'

const staffLocations = ref([])
const busUsage = ref([])
const routePerformance = ref([])
const error = ref(null)

onMounted(async () => {
  try {
    staffLocations.value = await getStaffLocationHistory()
    busUsage.value = await getBusUsage()
    routePerformance.value = await getRoutePerformance()
  } catch (err) {
    error.value = err || 'Failed to load reports'
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean tables, smooth hover handled globally */
</style>
