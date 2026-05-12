<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Staff Dashboard
      </h2>

      <!-- ✅ Profile Card -->
      <div v-if="profile" class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          {{ profile.name }}
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Email:</span> {{ profile.email }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Role:</span> {{ profile.role }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Assigned Bus:</span> {{ profile.busDisplayName || 'N/A' }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          <span class="font-semibold">Route:</span> {{ profile.routeName || 'N/A' }}
        </p>
      </div>

      <!-- ✅ Location Sharing -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Share Live Location
        </h3>
        <div class="flex space-x-3">
          <button
              @click="startSharing"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Start Sharing
          </button>
          <button
              @click="stopSharing"
              class="px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 font-medium"
          >
            Stop Sharing
          </button>
        </div>
        <p v-if="locationStatus" class="text-sm text-green-600 dark:text-green-400">
          {{ locationStatus }}
        </p>
      </div>

      <!-- ✅ Bus Status Update -->
      <div v-if="profile && profile.busId" class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Update Bus Status
        </h3>
        <div class="flex space-x-3">
          <select v-model="busStatus" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
            <option value="Active">Active</option>
            <option value="Inactive">Inactive</option>
            <option value="Maintenance">Maintenance</option>
          </select>
          <button
              @click="updateStatus"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Update
          </button>
        </div>
        <p v-if="statusMessage" class="text-sm text-green-600 dark:text-green-400">
          {{ statusMessage }}
        </p>
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
import { getStaffProfile, updateStaffBusStatus } from '../services/userService'
import { addLocation } from '../services/locationService'

const profile = ref(null)
const locationStatus = ref('')
const intervalId = ref(null)
const busStatus = ref('Active')
const statusMessage = ref('')

onMounted(async () => {
  try {
    profile.value = await getStaffProfile()
  } catch (err) {
    console.error('Failed to load staff profile', err)
  }
})

// ✅ Start sharing location every 5s
const startSharing = () => {
  if (!profile.value || !profile.value.busId) {
    locationStatus.value = 'No bus assigned to you'
    return
  }

  if (navigator.geolocation) {
    fetchAndShare() // first immediate call
    intervalId.value = setInterval(fetchAndShare, 5000) // repeat every 5s
    locationStatus.value = 'Live location sharing started...'
  } else {
    locationStatus.value = 'Geolocation not supported'
  }
}

// ✅ Stop sharing
const stopSharing = () => {
  if (intervalId.value) {
    clearInterval(intervalId.value)
    intervalId.value = null
    locationStatus.value = 'Live location sharing stopped.'
  }
}

// ✅ Helper → fetch current position and send to backend
const fetchAndShare = () => {
  navigator.geolocation.getCurrentPosition(async (pos) => {
    const { latitude, longitude } = pos.coords
    try {
      await addLocation({
        busId: profile.value.busId,   // ✅ send busId
        latitude,
        longitude,
        speed: 0,
        tripType: 'MORNING' // default, can be dynamic later
      })
      locationStatus.value = `Location shared (Lat: ${latitude}, Lng: ${longitude})`
    } catch (err) {
      locationStatus.value = 'Failed to share location'
    }
  })
}

// ✅ Update bus status
const updateStatus = async () => {
  try {
    await updateStaffBusStatus(busStatus.value)
    statusMessage.value = `Bus status updated to ${busStatus.value}`
  } catch (err) {
    statusMessage.value = 'Failed to update bus status'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
