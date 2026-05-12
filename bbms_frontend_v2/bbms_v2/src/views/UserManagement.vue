<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400 mb-6">
        User Management
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading users...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ User Table -->
      <div v-if="users.length" class="overflow-x-auto">
        <table class="min-w-full bg-white dark:bg-gray-800 rounded-xl shadow-md">
          <thead>
          <tr class="bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200">
            <th class="px-4 py-2 text-left">Name</th>
            <th class="px-4 py-2 text-left">Email</th>
            <th class="px-4 py-2 text-left">Role</th>
            <th class="px-4 py-2 text-left">Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in users" :key="user.id" class="border-b dark:border-gray-700">
            <td class="px-4 py-2">{{ user.name }}</td>
            <td class="px-4 py-2">{{ user.email }}</td>
            <td class="px-4 py-2">
              <select
                  v-model="user.role"
                  @change="updateUserRole(user)"
                  class="px-2 py-1 rounded-md border dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
              >
                <option value="USER">USER</option>
                <option value="STAFF">STAFF</option>
                <option value="ADMIN">ADMIN</option>
                <option value="SUPER_ADMIN">SUPER_ADMIN</option>
              </select>
            </td>
            <td class="px-4 py-2 space-x-2">
              <button
                  @click="deleteUserHandler(user.id)"
                  class="px-3 py-1 rounded-md bg-red-500 text-white hover:bg-red-600 text-sm font-medium"
              >
                Delete
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- ✅ Empty State -->
      <div v-if="!loading && !error && users.length === 0" class="text-center text-gray-600 dark:text-gray-300">
        No users found.
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
import { getAllUsers, updateUser, deleteUser } from '../services/adminService'

const users = ref([])
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    users.value = await getAllUsers()
  } catch (err) {
    error.value = err || 'Failed to load users'
  } finally {
    loading.value = false
  }
})

// ✅ Update user role
const updateUserRole = async (user) => {
  try {
    await updateUser(user.id, { ...user })
  } catch (err) {
    error.value = err || 'Failed to update user role'
  }
}

// ✅ Delete user
const deleteUserHandler = async (id) => {
  try {
    await deleteUser(id)
    users.value = users.value.filter((u) => u.id !== id)
  } catch (err) {
    error.value = err || 'Failed to delete user'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → clean table, smooth hover */
</style>
