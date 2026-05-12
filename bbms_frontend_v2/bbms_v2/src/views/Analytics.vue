<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        System Analytics
      </h2>

      <!-- ✅ Active Users Chart -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Active Users
        </h3>
        <canvas id="usersChart"></canvas>
      </div>

      <!-- ✅ Bus Usage Chart -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Bus Usage
        </h3>
        <canvas id="busChart"></canvas>
      </div>

      <!-- ✅ Route Performance Chart -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Route Performance
        </h3>
        <canvas id="routeChart"></canvas>
      </div>

      <!-- ✅ Payments Chart -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Payments Overview
        </h3>
        <canvas id="paymentsChart"></canvas>
      </div>
    </main>

    <!-- ✅ Footer -->
    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../components/Navbar.vue'
import Footer from '../components/Footer.vue'
import { onMounted } from 'vue'
import Chart from 'chart.js/auto'
import { getUserStats, getBusStats, getRouteStats, getPaymentStats } from '../services/analyticsService'

onMounted(async () => {
  try {
    const users = await getUserStats()
    const buses = await getBusStats()
    const routes = await getRouteStats()
    const payments = await getPaymentStats()

    // ✅ Users Chart
    new Chart(document.getElementById('usersChart'), {
      type: 'bar',
      data: {
        labels: users.labels,
        datasets: [{
          label: 'Active Users',
          data: users.data,
          backgroundColor: 'rgba(99, 102, 241, 0.7)',
        }]
      }
    })

    // ✅ Bus Usage Chart
    new Chart(document.getElementById('busChart'), {
      type: 'line',
      data: {
        labels: buses.labels,
        datasets: [{
          label: 'Trips',
          data: buses.trips,
          borderColor: 'rgba(16, 185, 129, 0.7)',
          fill: false,
        }]
      }
    })

    // ✅ Route Performance Chart
    new Chart(document.getElementById('routeChart'), {
      type: 'bar',
      data: {
        labels: routes.labels,
        datasets: [{
          label: 'On-time %',
          data: routes.onTime,
          backgroundColor: 'rgba(245, 158, 11, 0.7)',
        }]
      }
    })

    // ✅ Payments Chart
    new Chart(document.getElementById('paymentsChart'), {
      type: 'pie',
      data: {
        labels: payments.labels,
        datasets: [{
          label: 'Payments',
          data: payments.data,
          backgroundColor: [
            'rgba(99, 102, 241, 0.7)',
            'rgba(16, 185, 129, 0.7)',
            'rgba(245, 158, 11, 0.7)',
            'rgba(239, 68, 68, 0.7)'
          ]
        }]
      }
    })
  } catch (err) {
    console.error('Failed to load analytics', err)
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
