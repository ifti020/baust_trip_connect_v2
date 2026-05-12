<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Admin Dashboard
      </h2>

      <!-- ✅ Promote User to Staff -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Promote User to Staff
        </h3>
        <div class="flex space-x-3 mb-4">
          <input
              v-model="staffEmail"
              type="email"
              placeholder="Enter user email"
              class="px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
          />
          <button
              @click="assignStaffHandler"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Promote
          </button>
        </div>
      </div>

      <!-- ✅ Staff List Table -->
      <StaffList
          :staffList="staffList"
          :busList="busList"
          @remove-staff="removeStaffHandler"
      />

      <!-- ✅ Add New Bus -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Add New Bus
        </h3>
        <div class="flex space-x-3 mb-4">
          <input
              v-model="newRouteName"
              type="text"
              placeholder="Enter Route Name (e.g. Dinajpur)"
              class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200"
          />
          <input
              v-model="newBusNumber"
              type="number"
              placeholder="Bus Number"
              class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200"
          />
          <button
              @click="addBusHandler"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Add Bus
          </button>
        </div>
      </div>

      <!-- ✅ Bus Assignment -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Assign Bus to Staff
        </h3>
        <div class="flex space-x-3 mb-4">
          <select v-model="selectedStaff" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
            <option disabled value="">Select Staff</option>
            <option v-for="staff in staffList" :key="staff.id" :value="staff.id">
              {{ staff.name }}
            </option>
          </select>
          <select v-model="selectedBus" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
            <option disabled value="">Select Bus</option>
            <option v-for="bus in busList" :key="bus.id" :value="bus.id">
              {{ bus.displayName }}
            </option>
          </select>
          <button
              @click="assignBusHandler"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Assign
          </button>
        </div>
      </div>

      <!-- ✅ Bus Status Update -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Update Bus Status
        </h3>
        <div class="flex space-x-3 mb-4">
          <select v-model="selectedBusStatusBus" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
            <option disabled value="">Select Bus</option>
            <option v-for="bus in busList" :key="bus.id" :value="bus.id">
              {{ bus.displayName }}
            </option>
          </select>
          <select v-model="busStatus" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
            <option value="Active">Active</option>
            <option value="Inactive">Inactive</option>
            <option value="Maintenance">Maintenance</option>
          </select>
          <button
              @click="updateBusStatusHandler"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Update Status
          </button>
        </div>
      </div>

      <!-- ✅ Bus Status Table -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          All Buses Status
        </h3>
        <table class="min-w-full text-sm text-gray-800 dark:text-gray-200">
          <thead>
          <tr class="bg-gray-200 dark:bg-gray-700">
            <th class="px-4 py-2">Bus</th>
            <th class="px-4 py-2">Route</th>
            <th class="px-4 py-2">Status</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="bus in busList" :key="bus.id" class="border-b dark:border-gray-600">
            <td class="px-4 py-2">{{ bus.displayName }}</td>
            <td class="px-4 py-2">{{ bus.routeName || '-' }}</td>
            <td class="px-4 py-2">{{ bus.status || 'Unknown' }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- ✅ User Management -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Manage Users
        </h3>
        <ul class="space-y-2">
          <li
              v-for="user in userList"
              :key="user.id"
              class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2"
          >
            <span class="text-sm text-gray-800 dark:text-gray-200">
              {{ user.name }} ({{ user.email }})
            </span>
            <router-link
                :to="`/users/${user.id}`"
                class="text-indigo-600 dark:text-indigo-400 text-xs font-medium hover:underline"
            >
              View Profile
            </router-link>
          </li>
        </ul>
      </div>
    </main>
  </div>
</template>
<script setup>
import Navbar from '../components/Navbar.vue'
import StaffList from '../views/StaffList.vue'
import { ref, onMounted } from 'vue'
import { getAllUsers, assignStaff, removeStaff, assignBus, updateBusStatus, addBus } from '../services/adminService'
import { getAllBuses } from '../services/busService'

const staffList = ref([])
const busList = ref([])
const userList = ref([])
const staffEmail = ref('')
const selectedStaff = ref('')
const selectedBus = ref('')
const selectedBusStatusBus = ref('')
const busStatus = ref('Active')
const newRouteName = ref('')
const newBusNumber = ref('')
const statusMessage = ref('')
const error = ref(null)

onMounted(async () => {
  try {
    const allUsers = await getAllUsers()
    staffList.value = allUsers.filter(u => u.role === 'STAFF')
    busList.value = await getAllBuses()
    userList.value = allUsers
  } catch (err) {
    error.value = err || 'Failed to load data'
  }
})

// ✅ Promote user to staff
const assignStaffHandler = async () => {
  try {
    const user = userList.value.find(u => u.email === staffEmail.value)
    if (user) {
      const updated = await assignStaff(user.id)
      staffList.value.push(updated)
    }
    staffEmail.value = ''
  } catch (err) {
    error.value = err || 'Failed to promote staff'
  }
}

// ✅ Remove staff
const removeStaffHandler = async (staffId) => {
  try {
    await removeStaff(staffId)
    staffList.value = staffList.value.filter(s => s.id !== staffId)
  } catch (err) {
    error.value = err || 'Failed to remove staff'
  }
}

// ✅ Add new bus (Dynamic name → RouteName + Number)
const addBusHandler = async () => {
  try {
    if (!newRouteName.value || !newBusNumber.value) {
      alert("Please enter both route name and bus number")
      return
    }
    const busData = {
      busNumber: newBusNumber.value,
      route: { routeName: newRouteName.value },
      status: 'Active'
    }
    const newBus = await addBus(busData)
    busList.value.push(newBus)
    newRouteName.value = ''
    newBusNumber.value = ''
  } catch (err) {
    error.value = err || 'Failed to add bus'
  }
}

// ✅ Assign bus to staff
const assignBusHandler = async () => {
  try {
    if (!selectedStaff.value || !selectedBus.value) {
      alert("Please select both staff and bus before assigning")
      return
    }
    const result = await assignBus(selectedStaff.value, selectedBus.value)
    const index = staffList.value.findIndex(s => s.id === result.id)
    if (index !== -1) {
      staffList.value[index] = result
    }
    selectedStaff.value = ''
    selectedBus.value = ''
  } catch (err) {
    error.value = err || 'Failed to assign bus'
  }
}

// ✅ Update bus status
const updateBusStatusHandler = async () => {
  try {
    const result = await updateBusStatus(selectedBusStatusBus.value, busStatus.value)
    const index = busList.value.findIndex(b => b.id === result.id)
    if (index !== -1) {
      busList.value[index] = result
    }
    statusMessage.value = `Bus status updated to ${busStatus.value}`
    selectedBusStatusBus.value = ''
    busStatus.value = 'Active'
  } catch (err) {
    statusMessage.value = 'Failed to update bus status'
  }
}
</script>
<style scoped>
/* Uber/Pathao style → clean table, smooth hover */
</style>
