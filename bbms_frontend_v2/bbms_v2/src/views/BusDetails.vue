<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Bus Details
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading bus details...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Bus Info Card -->
      <div v-if="bus" class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          BUS-{{ bus.busNumber }}
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Capacity:</span> {{ bus.capacity }} seats
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Status:</span>
          <span :class="bus.status === 'Active' ? 'text-green-600 dark:text-green-400' : 'text-red-500'">
            {{ bus.status }}
          </span>
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Route:</span> {{ bus.route?.routeName || 'N/A' }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Start Time:</span> {{ bus.route?.startTime || 'N/A' }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Driver:</span> {{ bus.driverName || 'N/A' }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Helper:</span> {{ bus.helperName || 'N/A' }}
        </p>

        <!-- ✅ Actions -->
        <div class="flex space-x-3 mt-4">
          <router-link
              :to="`/locations?busId=${bus.id}`"
              class="px-3 py-1 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 text-sm font-medium"
          >
            Track Live
          </router-link>
          <router-link
              to="/buses"
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
import { getBusById } from '../services/busService'

const route = useRoute()
const bus = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    bus.value = await getBusById(route.params.id)
  } catch (err) {
    error.value = err || 'Failed to load bus details'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean card, smooth hover handled globally */
</style>
