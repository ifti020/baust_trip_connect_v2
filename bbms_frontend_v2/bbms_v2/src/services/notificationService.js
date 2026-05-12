// 🚨 DUMMY SERVICE FILE — FIX LATER WITH REAL BACKEND ENDPOINTS
// Used in Notifications.vue

export const getNotifications = async () => {
    return [{ id: 1, message: 'Dummy notification', read: false }]
}

export const markNotificationRead = async (id) => {
    return { success: true, id }
}
