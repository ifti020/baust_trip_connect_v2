<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Guide Details
      </h2>

      <!-- ✅ Loading State -->
      <div v-if="loading" class="text-center text-gray-600 dark:text-gray-300">
        Loading guide...
      </div>

      <!-- ✅ Error State -->
      <div v-if="error" class="text-center text-red-500">
        {{ error }}
      </div>

      <!-- ✅ Guide Content -->
      <div v-if="guide" class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          {{ guide.title }}
        </h3>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          {{ guide.description }}
        </p>

        <!-- ✅ Steps -->
        <div class="mt-4 space-y-3">
          <h4 class="text-md font-semibold text-indigo-600 dark:text-indigo-400">Steps:</h4>
          <ol class="list-decimal list-inside space-y-2">
            <li v-for="(step, index) in guide.steps" :key="index" class="text-sm text-gray-700 dark:text-gray-300">
              {{ step }}
            </li>
          </ol>
        </div>

        <!-- ✅ Related Links -->
        <div v-if="guide.links && guide.links.length" class="mt-4 space-y-2">
          <h4 class="text-md font-semibold text-indigo-600 dark:text-indigo-400">Related Links:</h4>
          <ul class="list-disc list-inside">
            <li v-for="link in guide.links" :key="link.url">
              <a :href="link.url" target="_blank" class="text-indigo-600 dark:text-indigo-400 hover:underline">
                {{ link.label }}
              </a>
            </li>
          </ul>
        </div>

        <!-- ✅ Back Button -->
        <router-link
            to="/help"
            class="mt-4 inline-block px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 hover:bg-gray-300 dark:hover:bg-gray-600 text-sm font-medium"
        >
          Back to Help Center
        </router-link>
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
import { useRoute } from 'vue-router'
import { getGuideById } from '../services/helpService'

const route = useRoute()
const guide = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    guide.value = await getGuideById(route.params.id)
  } catch (err) {
    error.value = err || 'Failed to load guide'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
