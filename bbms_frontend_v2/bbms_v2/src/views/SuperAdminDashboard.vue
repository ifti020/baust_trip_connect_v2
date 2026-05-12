<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Super Admin Dashboard
      </h2>

      <!-- ✅ Create Admin -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Create New Admin
        </h3>
        <div class="flex space-x-3 mb-4">
          <input
              v-model="adminName"
              type="text"
              placeholder="Enter admin name"
              class="px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
          />
          <input
              v-model="adminEmail"
              type="email"
              placeholder="Enter admin email"
              class="px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
          />
          <input
              v-model="adminPassword"
              type="password"
              placeholder="Enter password"
              class="px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
          />
          <button
              @click="createAdmin"
              class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
          >
            Create
          </button>
        </div>
      </div>

      <!-- ✅ Manage Admins -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Manage Admins
        </h3>
        <ul class="space-y-2">
          <li
              v-for="admin in adminList"
              :key="admin.id"
              class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2"
          >
            <span class="text-sm text-gray-800 dark:text-gray-200">
              {{ admin.name }} ({{ admin.email }})
            </span>
            <button
                @click="deleteAdmin(admin.id)"
                class="px-2 py-1 bg-red-500 text-white rounded-md hover:bg-red-600 text-xs font-medium"
            >
              Delete
            </button>
          </li>
        </ul>
      </div>

      <!-- ✅ System Overview -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          System Overview
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          Total Users: {{ stats.users }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          Total Staff: {{ stats.staff }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          Total Buses: {{ stats.buses }}
        </p>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          Total Routes: {{ stats.routes }}
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
import { getAllAdmins, createNewAdmin, deleteAdminById, getSystemStats } from '../services/superAdminService'

const adminList = ref([])
const adminName = ref('')
const adminEmail = ref('')
const adminPassword = ref('')
const stats = ref({ users: 0, staff: 0, buses: 0, routes: 0 })
const error = ref(null)

onMounted(async () => {
  try {
    adminList.value = await getAllAdmins()
    stats.value = await getSystemStats()
  } catch (err) {
    error.value = err || 'Failed to load data'
  }
})

// ✅ Create new admin
const createAdmin = async () => {
  try {
    await createNewAdmin({
      name: adminName.value,
      email: adminEmail.value,
      password: adminPassword.value,
    })
    adminList.value = await getAllAdmins()
    adminName.value = ''
    adminEmail.value = ''
    adminPassword.value = ''
  } catch (err) {
    error.value = err || 'Failed to create admin'
  }
}

// ✅ Delete admin
const deleteAdmin = async (adminId) => {
  try {
    await deleteAdminById(adminId)
    adminList.value = adminList.value.filter((a) => a.id !== adminId)
  } catch (err) {
    error.value = err || 'Failed to delete admin'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
