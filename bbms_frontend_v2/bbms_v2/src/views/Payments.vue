<template>
  <div class="min-h-screen bg-gray-100 dark:bg-gray-900 flex flex-col">
    <!-- ✅ Navbar -->
    <Navbar />

    <!-- ✅ Content -->
    <main class="flex-1 container mx-auto px-4 py-6 space-y-6">
      <h2 class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">
        Payments & Billing
      </h2>

      <!-- ✅ Payment History -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Payment History
        </h3>
        <table class="w-full text-sm text-left text-gray-700 dark:text-gray-300">
          <thead>
          <tr class="bg-gray-200 dark:bg-gray-700">
            <th class="px-3 py-2">Date</th>
            <th class="px-3 py-2">Amount</th>
            <th class="px-3 py-2">Method</th>
            <th class="px-3 py-2">Status</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="pay in payments" :key="pay.id" class="border-b dark:border-gray-600">
            <td class="px-3 py-2">{{ formatDate(pay.date) }}</td>
            <td class="px-3 py-2">{{ pay.amount }} ৳</td>
            <td class="px-3 py-2">{{ pay.method }}</td>
            <td class="px-3 py-2">
                <span
                    :class="pay.status === 'Completed' ? 'text-green-600 dark:text-green-400' : 'text-red-500'"
                >
                  {{ pay.status }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- ✅ Make Payment -->
      <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 space-y-4">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          Make a Payment
        </h3>
        <input
            v-model="amount"
            type="number"
            placeholder="Enter amount"
            class="px-3 py-2 border rounded-md focus:ring-indigo-500 focus:border-indigo-500
                 dark:bg-gray-700 dark:text-gray-200 dark:border-gray-600"
        />
        <select v-model="method" class="px-3 py-2 border rounded-md dark:bg-gray-700 dark:text-gray-200">
          <option disabled value="">Select Method</option>
          <option value="Bkash">Bkash</option>
          <option value="Nagad">Nagad</option>
          <option value="Rocket">Rocket</option>
          <option value="Card">Card</option>
        </select>
        <button
            @click="makePayment"
            class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 font-medium"
        >
          Pay Now
        </button>
        <p v-if="paymentMessage" class="text-sm text-green-600 dark:text-green-400">
          {{ paymentMessage }}
        </p>
      </div>

      <!-- ✅ Admin Alerts -->
      <div v-if="alerts.length" class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
        <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
          Payment Alerts (Admin/Super Admin)
        </h3>
        <ul class="space-y-2">
          <li
              v-for="alert in alerts"
              :key="alert.id"
              class="flex items-center justify-between bg-gray-100 dark:bg-gray-700 rounded-md px-3 py-2"
          >
            <span class="text-sm text-gray-800 dark:text-gray-200">
              {{ alert.message }}
            </span>
            <span class="text-xs text-gray-500 dark:text-gray-400">
              {{ formatDate(alert.timestamp) }}
            </span>
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
import { getPayments, createPayment, getPaymentAlerts } from '../services/paymentService'

const payments = ref([])
const alerts = ref([])
const amount = ref('')
const method = ref('')
const paymentMessage = ref('')
const error = ref(null)

onMounted(async () => {
  try {
    payments.value = await getPayments()
    alerts.value = await getPaymentAlerts()
  } catch (err) {
    error.value = err || 'Failed to load payments'
  }
})

// ✅ Make payment
const makePayment = async () => {
  try {
    await createPayment({ amount: amount.value, method: method.value })
    paymentMessage.value = `Payment of ${amount.value} ৳ via ${method.value} successful!`
    payments.value = await getPayments()
    amount.value = ''
    method.value = ''
  } catch (err) {
    paymentMessage.value = 'Payment failed'
  }
}

// ✅ Format date
const formatDate = (timestamp) => {
  return new Date(timestamp).toLocaleString()
}
</script>

<style scoped>
/* Uber/Pathao style → clean cards, smooth hover handled globally */
</style>
