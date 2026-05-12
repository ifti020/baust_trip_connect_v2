<template>
  <div
      class="bg-white dark:bg-gray-800 rounded-xl shadow-md p-6 hover:shadow-lg transition flex flex-col justify-between"
  >
    <!-- ✅ Bus Header -->
    <h3 class="text-lg font-semibold text-indigo-600 dark:text-indigo-400 mb-2">
      {{ bus.displayName }}
    </h3>

    <!-- ✅ Bus Info -->
    <p class="text-sm text-gray-700 dark:text-gray-300">
      Capacity: {{ bus.capacity || 'N/A' }} seats
    </p>
    <p class="text-sm text-gray-700 dark:text-gray-300">
      Status:
      <span
          :class="bus.status === 'Active'
          ? 'text-green-600 dark:text-green-400'
          : bus.status === 'Maintenance'
            ? 'text-yellow-600 dark:text-yellow-400'
            : 'text-red-500'"
      >
        {{ bus.status }}
      </span>
    </p>
    <p class="text-sm text-gray-700 dark:text-gray-300">
      Route: {{ bus.routeName || 'N/A' }}
    </p>
    <p class="text-sm text-gray-700 dark:text-gray-300">
      Driver: {{ bus.driverName || 'N/A' }}
    </p>
    <p class="text-sm text-gray-700 dark:text-gray-300">
      Helper: {{ bus.helperName || 'N/A' }}
    </p>

    <!-- ✅ Actions -->
    <div class="flex space-x-3 mt-4">
      <button
          @click="$emit('trackBus', bus.id)"
          class="px-3 py-1 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 text-sm font-medium"
      >
        Track
      </button>
      <button
          @click="$emit('details', bus.id)"
          class="px-3 py-1 rounded-md bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200 hover:bg-gray-300 dark:hover:bg-gray-600 text-sm font-medium"
      >
        Details
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  bus: {
    type: Object,
    required: true
  }
})
</script>

<style scoped>
/* Uber/Pathao style → clean card, smooth hover handled globally */
</style>
