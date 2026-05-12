<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-900">
    <div
        class="w-full max-w-md bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 space-y-6"
    >
      <!-- ✅ Logo -->
      <div class="flex justify-center">
        <!-- এখানে register-logo.png বসাও -->
        <!-- <img src="@/assets/register-logo.png" alt="Logo" class="h-12 w-12" /> -->
      </div>

      <!-- ✅ Title -->
      <h2 class="text-2xl font-bold text-center text-indigo-600 dark:text-indigo-400">
        Create Account
      </h2>
      <p class="text-center text-gray-600 dark:text-gray-300 text-sm">
        Sign up to start your journey
      </p>

      <!-- ✅ Register Form -->
      <form @submit.prevent="handleRegister" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
            Name
          </label>
          <input
              v-model="name"
              type="text"
              required
              class="mt-1 w-full px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
              placeholder="Enter your name"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
            Email
          </label>
          <input
              v-model="email"
              type="email"
              required
              class="mt-1 w-full px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
              placeholder="Enter your email"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300">
            Password
          </label>
          <input
              v-model="password"
              type="password"
              required
              class="mt-1 w-full px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                   dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
              placeholder="Enter your password"
          />
        </div>

        <!-- ✅ Error Message -->
        <p v-if="error" class="text-red-500 text-sm">{{ error }}</p>

        <!-- ✅ Submit Button -->
        <button
            type="submit"
            class="w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white rounded-md font-medium"
        >
          Register
        </button>
      </form>

      <!-- ✅ Login Link -->
      <p class="text-center text-sm text-gray-600 dark:text-gray-300">
        Already have an account?
        <router-link to="/login" class="text-indigo-600 dark:text-indigo-400 font-medium">
          Login
        </router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { register } from '../services/authService'
import { useRouter } from 'vue-router'

const name = ref('')
const email = ref('')
const password = ref('')
const error = ref(null)
const router = useRouter()

const handleRegister = async () => {
  try {
    const response = await register({
      name: name.value,
      email: email.value,
      password: password.value,
    })
    // ✅ Registration successful → redirect to login
    router.push('/login')
  } catch (err) {
    error.value = err || 'Registration failed'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → smooth card, rounded corners, shadow */
</style>
