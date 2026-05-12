<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400 mb-6">
        Available Routes
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading routes...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Route Grid -->
      <div v-if="routes.length" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
            v-for="route in routes"
            :key="route.id"
            class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 hover:shadow-lg hover:scale-105 transition transform"
        >
          <!-- Header -->
          <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-2">
            {{ route.routeName }}
          </h3>

          <!-- ✅ Schedule Info -->
          <p class="text-sm text-gray-700 dark:text-gray-300">
            <span class="font-semibold">Morning Departure:</span> {{ route.morningDeparture || 'N/A' }}
          </p>
          <p class="text-sm text-gray-700 dark:text-gray-300">
            <span class="font-semibold">Morning Arrival:</span> {{ route.morningArrival || 'N/A' }}
          </p>
          <p class="text-sm text-gray-700 dark:text-gray-300">
            <span class="font-semibold">Afternoon Departure:</span> {{ route.afternoonDeparture || 'N/A' }}
          </p>
          <p class="text-sm text-gray-700 dark:text-gray-300">
            <span class="font-semibold">Afternoon Arrival:</span> {{ route.afternoonArrival || 'N/A' }}
          </p>

          <!-- Actions -->
          <div class="mt-4 flex space-x-3">
            <button
                @click="viewDetails(route.id)"
                class="px-3 py-1 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 text-sm font-medium"
            >
              Details
            </button>
            <button
                @click="deleteRouteHandler(route.id)"
                class="px-3 py-1 rounded-md bg-red-500 text-white hover:bg-red-600 text-sm font-medium"
            >
              Delete
            </button>
          </div>
        </div>
      </div>

      <!-- ✅ Empty State -->
      <div v-if="!loading && !error && routes.length === 0" class="text-center text-gray-600 dark:text-gray-300">
        No routes available.
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
import { getAllRoutes, deleteRoute } from '../services/routeService'

const routes = ref([])
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    routes.value = await getAllRoutes()
  } catch (err) {
    error.value = err || 'Failed to load routes'
  } finally {
    loading.value = false
  }
})

// ✅ Actions
const viewDetails = (routeId) => {
  // Future: redirect to /routes/:id
  console.log('View details of route:', routeId)
}

const deleteRouteHandler = async (routeId) => {
  try {
    await deleteRoute(routeId)
    routes.value = routes.value.filter((r) => r.id !== routeId)
  } catch (err) {
    error.value = err || 'Failed to delete route'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → clean grid, smooth hover */
</style>
