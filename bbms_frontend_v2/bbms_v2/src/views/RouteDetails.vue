<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Route Details
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading route details...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Route Info Card -->
      <div
          v-if="route"
          class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4"
      >
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
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

        <!-- ✅ Assigned Buses -->
        <div class="mt-4">
          <h4 class="text-md font-semibold text-indigo-600 dark:text-indigo-400 mb-2">
            Assigned Buses
          </h4>
          <div v-if="route.buses && route.buses.length" class="space-y-2">
            <div
                v-for="bus in route.buses"
                :key="bus.id"
                class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2"
            >
              <span class="text-sm text-gray-800 dark:text-gray-200">
                {{ bus.displayName }}
              </span>
              <router-link
                  :to="`/buses/${bus.id}`"
                  class="text-indigo-600 dark:text-indigo-400 text-sm font-medium hover:underline"
              >
                View Details
              </router-link>
            </div>
          </div>
          <p v-else class="text-sm text-gray-600 dark:text-gray-300">
            No buses assigned to this route.
          </p>
        </div>

        <!-- ✅ Actions -->
        <div class="flex space-x-3 mt-4">
          <router-link
              to="/routes"
              class="px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 hover:bg-gray-300 dark:hover:bg-gray-600 text-sm font-medium"
          >
            Back to List
          </router-link>
        </div>
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
import { useRoute } from 'vue-router'
import { getRouteById } from '../services/routeService'

const routeParam = useRoute()
const route = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const response = await getRouteById(routeParam.params.id)
    route.value = response
  } catch (err) {
    error.value = err || 'Failed to load route details'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean card, smooth hover handled globally */
</style>
