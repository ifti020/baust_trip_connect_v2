<template>
  <div class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6">
    <!-- ✅ Staff Table -->
    <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
      Staff List
    </h3>
    <table class="min-w-full text-sm text-gray-800 dark:text-gray-200 mb-6">
      <thead>
      <tr class="bg-gray-200 dark:bg-gray-700">
        <th class="px-4 py-2">Name</th>
        <th class="px-4 py-2">Email</th>
        <th class="px-4 py-2">Bus</th>
        <th class="px-4 py-2">Route</th>
        <th class="px-4 py-2">Action</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="staff in staffList" :key="staff.id" class="border-b dark:border-gray-600">
        <td class="px-4 py-2">{{ staff.name }}</td>
        <td class="px-4 py-2">{{ staff.email }}</td>
        <td class="px-4 py-2">{{ staff.busDisplayName || 'Not Assigned' }}</td>
        <td class="px-4 py-2">{{ staff.routeName || '-' }}</td>
        <td class="px-4 py-2">
          <button
              @click="$emit('remove-staff', staff.id)"
              class="px-2 py-1 bg-red-500 text-white rounded-md hover:bg-red-600 text-xs font-medium"
          >
            Remove
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- ✅ Bus Status Table -->
    <h3 class="text-lg font-bold text-indigo-600 dark:text-indigo-400 mb-4">
      Bus Status
    </h3>
    <table class="min-w-full text-sm text-gray-800 dark:text-gray-200">
      <thead>
      <tr class="bg-gray-200 dark:bg-gray-700">
        <th class="px-4 py-2">Bus</th>
        <th class="px-4 py-2">Route</th>
        <th class="px-4 py-2">Status</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="bus in busList" :key="bus.id" class="border-b dark:border-gray-600">
        <td class="px-4 py-2">{{ bus.displayName }}</td>
        <td class="px-4 py-2">{{ bus.routeName || '-' }}</td>
        <td class="px-4 py-2">{{ bus.status || 'Unknown' }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
defineProps({
  staffList: {
    type: Array,
    required: true
  },
  busList: {
    type: Array,
    default: () => []   // ✅ এখন default empty array
  }
})
</script>

<style scoped>
table {
  border-collapse: collapse;
  width: 100%;
}
th, td {
  text-align: left;
}
</style>
