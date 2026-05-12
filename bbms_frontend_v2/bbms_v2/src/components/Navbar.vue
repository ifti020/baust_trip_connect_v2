<template>
  <nav
      class="w-full px-4 py-3 flex items-center justify-between
           bg-white dark:bg-gray-900 shadow-md fixed top-0 z-50"
  >
    <!-- ✅ Logo -->
    <div class="flex items-center space-x-2">
      <!-- এখানে logo.png বসাও -->
       <img src="../assets/logo.png" alt="Logo" class="h-8 w-8" />
      <span class="text-xl font-bold text-indigo-600 dark:text-indigo-400">
        BAUST Trip Connect
      </span>
    </div>

    <!-- ✅ Navigation Links -->
    <div class="hidden md:flex space-x-6">
      <router-link to="/dashboard" class="nav-link">Dashboard</router-link>
      <router-link to="/buses" class="nav-link">Buses</router-link>
      <router-link to="/routes" class="nav-link">Routes</router-link>
      <router-link to="/locations" class="nav-link">Live Map</router-link>
    </div>

    <!-- ✅ Right Side (Dark Mode + Login) -->
    <div class="flex items-center space-x-4">
      <!-- এখানে dark mode icon বসাও -->
       <img src="../assets/moon-icon.svg" alt="Dark Mode" class="h-6 w-6" />
      <button
          @click="toggleDarkMode"
          class="px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 font-medium"
      >
        {{ isDark ? 'Light Mode' : 'Dark Mode' }}
      </button>

      <!-- এখানে user icon বসাও -->
       <img src="../assets/user-icon.svg" alt="User" class="h-6 w-6" />
      <router-link
          to="/login"
          class="px-3 py-1 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 font-medium"
      >
        Login
      </router-link>
    </div>

    <!-- ✅ Mobile Menu Button -->
    <button
        @click="toggleMenu"
        class="md:hidden text-gray-700 dark:text-gray-200 focus:outline-none"
    >
      <!-- এখানে hamburger icon বসাও -->
       <img src="../assets/menu-icon.svg" alt="Menu" class="h-6 w-6" />
<!--      ☰-->
    </button>

    <!-- ✅ Mobile Dropdown -->
    <div
        v-if="isOpen"
        class="absolute top-16 left-0 w-full bg-white dark:bg-gray-900 shadow-md md:hidden flex flex-col space-y-2 p-4"
    >
      <router-link to="/dashboard" class="nav-link">Dashboard</router-link>
      <router-link to="/buses" class="nav-link">Buses</router-link>
      <router-link to="/routes" class="nav-link">Routes</router-link>
      <router-link to="/locations" class="nav-link">Live Map</router-link>
      <router-link to="/login" class="nav-link">Login</router-link>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'

const isOpen = ref(false)
const toggleMenu = () => {
  isOpen.value = !isOpen.value
}

// ✅ Dark Mode Toggle
const isDark = ref(false)

const toggleDarkMode = () => {
  isDark.value = !isDark.value
  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}

// ✅ Load saved theme from localStorage
onMounted(() => {
  if (localStorage.getItem('theme') === 'dark') {
    isDark.value = true
    document.documentElement.classList.add('dark')
  }
})

// ✅ Save theme preference
watch(isDark, (val) => {
  localStorage.setItem('theme', val ? 'dark' : 'light')
})
</script>

<style scoped>
.nav-link {
  @apply text-gray-700 dark:text-gray-200 hover:text-indigo-600 dark:hover:text-indigo-400 font-medium;
}
</style>
