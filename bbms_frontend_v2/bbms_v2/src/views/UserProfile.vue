<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        My Profile
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading profile...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Profile Card -->
      <div
          v-if="user"
          class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4"
      >
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          {{ user.name }}
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Email:</span> {{ user.email }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Role:</span> {{ user.role }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Assigned Bus:</span> {{ user.busDisplayName || 'N/A' }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Route:</span> {{ user.routeName || 'N/A' }}
        </p>

        <!-- ✅ Actions -->
        <div class="flex space-x-3 mt-4">
          <button
              @click="editProfile"
              class="px-3 py-1 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 text-sm font-medium"
          >
            Edit Profile
          </button>
          <router-link
              to="/dashboard"
              class="px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 hover:bg-gray-300 dark:hover:bg-gray-600 text-sm font-medium"
          >
            Back to Dashboard
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
import { getUserById } from '../services/adminService.js'

const user = ref(null)
const loading = ref(true)
const error = ref(null)

// ✅ Load user from localStorage (saved at login)
onMounted(async () => {
  try {
    const storedUser = localStorage.getItem('user')
    if (storedUser) {
      const parsed = JSON.parse(storedUser)
      user.value = await getUserById(parsed.id)
    }
  } catch (err) {
    error.value = err || 'Failed to load profile'
  } finally {
    loading.value = false
  }
})

// ✅ Edit profile (future expansion)
const editProfile = () => {
  console.log('Edit profile clicked')
  // Future: open modal or redirect to /profile/edit
}
</script>

<!--<style scoped>-->
<!--/* Uber/Pathao style → clean card, smooth hover handled globally */-->
<!--</style>-->
