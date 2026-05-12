<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Dashboard Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <!-- ✅ Welcome Section -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
          Welcome, {{ user?.name }}
        </h2>
        <p class="text-gray-700 dark:text-gray-300 text-sm">
          Role: {{ user?.role }}
        </p>
      </div>

      <!-- ✅ Quick Actions -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <router-link
            to="/buses"
            class="dashboard-card"
        >
          <!-- এখানে bus-icon.svg বসাও -->
          <!-- <img src="@/assets/bus-icon.svg" alt="Bus" class="h-8 w-8" /> -->
          <span>Buses</span>
        </router-link>

        <router-link
            to="/routes"
            class="dashboard-card"
        >
          <!-- এখানে route-icon.svg বসাও -->
          <!-- <img src="@/assets/route-icon.svg" alt="Route" class="h-8 w-8" /> -->
          <span>Routes</span>
        </router-link>

        <router-link
            to="/locations"
            class="dashboard-card"
        >
          <!-- এখানে map-icon.svg বসাও -->
          <!-- <img src="@/assets/map-icon.svg" alt="Map" class="h-8 w-8" /> -->
          <span>Live Map</span>
        </router-link>
      </div>

      <!-- ✅ Profile Section -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Your Profile
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Email:</span> {{ user?.email }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Assigned Bus:</span> {{ user?.busAssigned || 'N/A' }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Route:</span> {{ user?.routeName || 'N/A' }}
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

const user = ref(null)

onMounted(() => {
  // ✅ Load user from localStorage (saved at login)
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
  }
})
</script>

<style scoped>
.dashboard-card {
  @apply flex flex-col items-center justify-center bg-white dark:bg-gray-800
  rounded-xl shadow-md p-6 space-y-2 text-indigo-600 dark:text-indigo-400
  font-bold hover:shadow-lg hover:scale-105 transition transform;
}
</style>
