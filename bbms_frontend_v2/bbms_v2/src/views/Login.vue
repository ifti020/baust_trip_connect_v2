<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-900">
    <div class="w-full max-w-md bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 space-y-6">
      <!-- ✅ Title -->
      <h2 class="text-2xl font-bold text-center text-indigo-600 dark:text-indigo-400">
        Welcome Back
      </h2>
      <p class="text-center text-gray-600 dark:text-gray-300 text-sm">
        Sign in to continue your journey
      </p>

      <!-- ✅ Login Form -->
      <form @submit.prevent="handleLogin" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Email</label>
          <input v-model="email" type="email" required
                 class="mt-1 w-full px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                        dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
                 placeholder="Enter your email"/>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">Password</label>
          <input v-model="password" type="password" required
                 class="mt-1 w-full px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                        dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
                 placeholder="Enter your password"/>
        </div>

        <!-- ✅ Error Message -->
        <p v-if="error" class="text-red-500 text-sm">{{ error }}</p>

        <!-- ✅ Submit Button -->
        <button type="submit"
                class="w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white rounded-md font-medium">
          Login
        </button>
      </form>

      <!-- ✅ Register Link -->
      <p class="text-center text-sm text-gray-600 dark:text-gray-300">
        Don’t have an account?
        <router-link to="/register" class="text-indigo-600 dark:text-indigo-400 font-medium">Register</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { login } from '../services/authService'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const error = ref(null)
const router = useRouter()

const handleLogin = async () => {
  try {
    const response = await login({ email: email.value, password: password.value })

    // ✅ Save token & user info
    localStorage.setItem('token', response.token)
    localStorage.setItem('user', JSON.stringify({
      id: response.id,
      name: response.name,
      email: response.email,
      role: response.role
    }))
    localStorage.setItem('userRole', response.role)

    // ✅ Redirect by role
    if (response.role === 'ADMIN') {
      router.push('/admin')
    } else if (response.role === 'STAFF') {
      router.push('/staff')
    } else {
      router.push('/dashboard')
    }
  } catch (err) {
    error.value = err || 'Invalid credentials'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → smooth card, rounded corners, shadow */
</style>
