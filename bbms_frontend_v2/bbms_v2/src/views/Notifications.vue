<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        System Notifications
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading notifications...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Notifications List -->
      <div v-if="notifications.length" class="space-y-4">
        <div
            v-for="note in notifications"
            :key="note.id"
            class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-4 flex justify-between items-center hover:shadow-lg transition"
        >
          <div>
            <h3 class="text-md font-semibold text-indigo-600 dark:text-indigo-400">
              {{ note.title }}
            </h3>
            <p class="text-sm text-gray-700 dark:text-gray-300">
              {{ note.message }}
            </p>
            <p class="text-xs text-gray-500 dark:text-gray-400">
              {{ formatDate(note.timestamp) }}
            </p>
          </div>
          <button
              @click="markAsRead(note.id)"
              class="px-3 py-1 bg-green-500 text-white rounded-md hover:bg-green-600 text-xs font-medium"
          >
            Mark as Read
          </button>
        </div>
      </div>

      <!-- ✅ Empty State -->
      <div v-if="!loading && !error && notifications.length === 0" class="text-center text-gray-600 dark:text-gray-300">
        No notifications available.
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
import { getNotifications, markNotificationRead } from '../services/notificationService'

const notifications = ref([])
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    notifications.value = await getNotifications()
  } catch (err) {
    error.value = err || 'Failed to load notifications'
  } finally {
    loading.value = false
  }
})

// ✅ Mark notification as read
const markAsRead = async (id) => {
  try {
    await markNotificationRead(id)
    notifications.value = notifications.value.filter((n) => n.id !== id)
  } catch (err) {
    error.value = err || 'Failed to mark notification'
  }
}

// ✅ Format date
const formatDate = (timestamp) => {
  return new Date(timestamp).toLocaleString()
}
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
