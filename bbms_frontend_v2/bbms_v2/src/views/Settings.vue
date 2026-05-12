<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Settings & Preferences
      </h2>

      <!-- ✅ Theme Settings -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Theme
        </h3>
        <select v-model="theme" @change="updateTheme" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
          <option value="light">Light Mode</option>
          <option value="dark">Dark Mode</option>
        </select>
      </div>

      <!-- ✅ Notifications Settings -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Notifications
        </h3>
        <label class="flex items-center space-x-2">
          <input type="checkbox" v-model="notificationsEnabled" @change="updateNotifications" />
          <span class="text-sm text-gray-700 dark:text-gray-300">Enable Notifications</span>
        </label>
      </div>

      <!-- ✅ Language Settings -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Language
        </h3>
        <select v-model="language" @change="updateLanguage" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
          <option value="en">English</option>
          <option value="bn">বাংলা</option>
        </select>
      </div>

      <!-- ✅ Save Confirmation -->
      <p v-if="message" class="text-sm text-green-600 dark:text-green-400">
        {{ message }}
      </p>
    </main>

    <!-- ✅ Footer -->
    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../components/Navbar.vue'
import Footer from '../components/Footer.vue'
import { ref, onMounted } from 'vue'
import { getSettings, updateThemeSetting, updateNotificationSetting, updateLanguageSetting } from '../services/settingsService'

const theme = ref('light')
const notificationsEnabled = ref(true)
const language = ref('en')
const message = ref('')

onMounted(async () => {
  try {
    const settings = await getSettings()
    theme.value = settings.theme
    notificationsEnabled.value = settings.notifications
    language.value = settings.language
  } catch (err) {
    console.error('Failed to load settings', err)
  }
})

// ✅ Update theme
const updateTheme = async () => {
  try {
    await updateThemeSetting(theme.value)
    message.value = `Theme updated to ${theme.value}`
  } catch (err) {
    message.value = 'Failed to update theme'
  }
}

// ✅ Update notifications
const updateNotifications = async () => {
  try {
    await updateNotificationSetting(notificationsEnabled.value)
    message.value = notificationsEnabled.value ? 'Notifications enabled' : 'Notifications disabled'
  } catch (err) {
    message.value = 'Failed to update notifications'
  }
}

// ✅ Update language
const updateLanguage = async () => {
  try {
    await updateLanguageSetting(language.value)
    message.value = `Language updated to ${language.value}`
  } catch (err) {
    message.value = 'Failed to update language'
  }
}
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
