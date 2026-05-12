<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Help Center
      </h2>

      <!-- ✅ FAQ Section -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Frequently Asked Questions
        </h3>
        <ul class="space-y-3">
          <li v-for="faq in faqs" :key="faq.id" class="border-b dark:border-gray-700 pb-2">
            <p class="text-sm font-semibold text-gray-800 dark:text-gray-200">
              {{ faq.question }}
            </p>
            <p class="text-sm text-gray-600 dark:text-gray-300">
              {{ faq.answer }}
            </p>
          </li>
        </ul>
      </div>

      <!-- ✅ Support Contact -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Contact Support
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          If you need further assistance, please reach out:
        </p>
        <ul class="text-sm text-gray-700 dark:text-gray-300 space-y-2">
          <li>Email: <a href="mailto:support@careconnect.com" class="text-indigo-600 dark:text-indigo-400">support@careconnect.com</a></li>
          <li>Phone: <a href="tel:+880123456789" class="text-indigo-600 dark:text-indigo-400">+880123456789</a></li>
          <li>Live Chat: Available 9 AM – 9 PM</li>
        </ul>
      </div>

      <!-- ✅ Quick Guides -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Quick Guides
        </h3>
        <ul class="space-y-2">
          <li v-for="guide in guides" :key="guide.id" class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2">
            <span class="text-sm text-gray-800 dark:text-gray-200">
              {{ guide.title }}
            </span>
            <router-link
                :to="`/guides/${guide.id}`"
                class="text-indigo-600 dark:text-indigo-400 text-xs font-medium hover:underline"
            >
              View Guide
            </router-link>
          </li>
        </ul>
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
import { getFaqs, getGuides } from '../services/helpService'

const faqs = ref([])
const guides = ref([])
const error = ref(null)

onMounted(async () => {
  try {
    faqs.value = await getFaqs()
    guides.value = await getGuides()
  } catch (err) {
    error.value = err || 'Failed to load help center data'
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
