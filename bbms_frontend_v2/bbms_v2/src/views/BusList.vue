<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400 mb-6">
        Available Buses
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading buses...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Bus List -->
      <div v-if="buses.length" class="space-y-4">
        <div
            v-for="bus in buses"
            :key="bus.id"
            class="flex items-center justify-between bg-white dark:bg-gray-800 rounded-xl shadow-md p-4 hover:shadow-lg cursor-pointer"
            @click="trackBus(bus.id)"
        >
          <!-- 🔹 Bus Name -->
          <span class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
            {{ bus.displayName }}
          </span>

          <!-- 🔹 Bus Status -->
          <span
              :class="bus.status === 'Active'
              ? 'text-green-600 dark:text-green-400'
              : bus.status === 'Maintenance'
                ? 'text-yellow-600 dark:text-yellow-400'
                : 'text-red-500'"
          >
            {{ bus.status }}
          </span>
        </div>
      </div>

      <!-- ✅ Empty State -->
      <div v-if="!loading && !error && buses.length === 0" class="text-center text-gray-600 dark:text-gray-300">
        No buses available.
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
import { useRouter } from 'vue-router'
import { getAllBuses } from '../services/busService'

const buses = ref([])
const loading = ref(true)
const error = ref(null)
const router = useRouter()

onMounted(async () => {
  try {
    // ✅ Backend থেকে সব bus আনবে (JWT header সহ)
    buses.value = await getAllBuses()
  } catch (err) {
    error.value = err || 'Failed to load buses'
  } finally {
    loading.value = false
  }
})

// ✅ Actions
const trackBus = (busId) => {
  // সরাসরি live location এ redirect করবে
  router.push(`/locations?busId=${busId}`)
}
</script>

<style scoped>
/* Uber/Pathao style → clean row list, smooth hover */
</style>
