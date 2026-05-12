<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <Navbar />

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

      <!-- ✅ Bus Table (Desktop) -->
      <div class="hidden md:block">
        <table class="min-w-full text-sm text-gray-800 dark:text-gray-200 rounded-lg overflow-hidden shadow-md">
          <thead>
          <tr class="bg-gray-200 dark:bg-gray-700">
            <th class="px-4 py-2">Bus Name</th>
            <th class="px-4 py-2">Status</th>
          </tr>
          </thead>
          <tbody>
          <tr
              v-for="bus in buses"
              :key="bus.id"
              class="border-b dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-700 cursor-pointer"
              @click="trackBus(bus.id)"
          >
            <td class="px-4 py-2 font-semibold text-indigo-600 dark:text-indigo-400">
              {{ bus.displayName }}
            </td>
            <td class="px-4 py-2">
                <span
                    :class="bus.status === 'Active'
                    ? 'text-green-600 dark:text-green-400'
                    : bus.status === 'Maintenance'
                      ? 'text-yellow-600 dark:text-yellow-400'
                      : 'text-red-500'"
                >
                  {{ bus.status }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- ✅ Bus Cards (Mobile) -->
      <div class="grid grid-cols-1 gap-4 md:hidden">
        <div
            v-for="bus in buses"
            :key="bus.id"
            class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4 hover:shadow-lg cursor-pointer"
            @click="trackBus(bus.id)"
        >
          <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
            {{ bus.displayName }}
          </h3>
          <p class="text-sm text-gray-700 dark:text-gray-300">
            Status:
            <span
                :class="bus.status === 'Active'
                ? 'text-green-600 dark:text-green-400'
                : bus.status === 'Maintenance'
                  ? 'text-yellow-600 dark:text-yellow-400'
                  : 'text-red-500'"
            >
              {{ bus.status }}
            </span>
          </p>
        </div>
      </div>

      <!-- ✅ Empty State -->
      <div v-if="!loading && !error && buses.length === 0" class="text-center text-gray-600 dark:text-gray-300">
        No buses available.
      </div>
    </main>

<!--    <Footer />-->
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
    buses.value = await getAllBuses()
  } catch (err) {
    error.value = err || 'Failed to load buses'
  } finally {
    loading.value = false
  }
})

// const trackBus = (busId) => {
//   router.push(`/locations?busId=${busId}`)
// }

const trackBus = (busId) => {
  router.push({ path: '/location-tracker', query: { busId } })
}

</script>

<style scoped>
table {
  border-collapse: collapse;
  width: 100%;
}
</style>
