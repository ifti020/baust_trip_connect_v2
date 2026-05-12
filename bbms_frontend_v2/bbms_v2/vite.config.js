// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
    plugins: [vue()],
    server: {
        host: '0.0.0.0',   // ✅ সব network interface এ bind করবে
        port: 5173         // ✅ তোমার dev server port
    }
})
