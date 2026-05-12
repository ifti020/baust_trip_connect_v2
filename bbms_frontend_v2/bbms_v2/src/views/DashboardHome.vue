<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Dashboard Home
      </h2>

      <!-- ✅ Role Info -->
      <div v-if="profile" class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 mb-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Welcome, {{ profile.name }}
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          Role: {{ profile.role }}
        </p>
      </div>

      <!-- ✅ Shortcut Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <router-link
            v-for="card in cards"
            :key="card.id"
            :to="card.link"
            class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 hover:shadow-lg transition flex flex-col justify-between"
        >
          <h3 class="text-lg font-semibold text-indigo-600 dark:text-indigo-400 mb-2">
            {{ card.title }}
          </h3>
          <p class="text-sm text-gray-700 dark:text-gray-300 flex-1">
            {{ card.description }}
          </p>
        </router-link>
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
import { getDashboardProfile, getDashboardCards } from '../services/dashboardService'

const profile = ref(null)
const cards = ref([])
const error = ref(null)

onMounted(async () => {
  try {
    profile.value = await getDashboardProfile()
    cards.value = await getDashboardCards(profile.value.role)
  } catch (err) {
    error.value = err || 'Failed to load dashboard'
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
