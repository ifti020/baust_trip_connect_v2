<template>
  <footer
      class="w-full px-6 py-4 bg-gray-100 dark:bg-gray-900 shadow-inner
           flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0"
  >

    <!-- ✅ Left Side: Logo + Text -->
    <div class="flex items-center space-x-2">
      <!-- এখানে footer-logo.png বসাও -->
      <!-- <img src="@/assets/footer-logo.png" alt="Logo" class="h-6 w-6" /> -->
      <span class="text-sm text-gray-700 dark:text-gray-300 font-medium">
        © 2026 BAUST Trip Connect
      </span>
    </div>

    <!-- ✅ Center: Navigation Links -->
    <div class="flex space-x-6">
      <router-link
          to="/dashboard"
          class="footer-link"
      >
        Dashboard
      </router-link>
      <router-link
          to="/buses"
          class="footer-link"
      >
        Buses
      </router-link>
      <router-link
          to="/routes"
          class="footer-link"
      >
        Routes
      </router-link>
      <router-link
          to="/locations"
          class="footer-link"
      >
        Live Map
      </router-link>
    </div>

    <!-- ✅ Right Side: Social Icons -->
    <div class="flex space-x-4">
      <!-- এখানে facebook-icon.svg বসাও -->
      <!-- <img src="@/assets/facebook-icon.svg" alt="Facebook" class="h-5 w-5 cursor-pointer" /> -->
      <!-- এখানে twitter-icon.svg বসাও -->
      <!-- <img src="@/assets/twitter-icon.svg" alt="Twitter" class="h-5 w-5 cursor-pointer" /> -->
      <!-- এখানে instagram-icon.svg বসাও -->
      <!-- <img src="@/assets/instagram-icon.svg" alt="Instagram" class="h-5 w-5 cursor-pointer" /> -->
    </div>
  </footer>
</template>

<script setup>
// Footer এ কোনো reactive logic দরকার নেই এখন,
// future এ dark mode toggle বা dynamic data চাইলে যোগ করা যাবে।
</script>

<style scoped>
.footer-link {
  @apply text-sm text-gray-700 dark:text-gray-300 hover:text-indigo-600 dark:hover:text-indigo-400 font-medium;
}
</style>
