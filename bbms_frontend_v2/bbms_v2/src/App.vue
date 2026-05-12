<template>
  <div class="min-h-screen flex flex-col bg-gray-100 dark:bg-gray-900">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Main Content -->
    <main class="flex-1 pt-16">
      <router-view />
    </main>

    <!-- ✅ Footer -->
<!--    <Footer />-->
  </div>
</template>

<script setup>
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'
</script>

<style>
/* Global styles for smooth dark/light mode transitions */
html {
  scroll-behavior: smooth;
}
body {
  @apply bg-gray-100 dark:bg-gray-900;
}
</style>
