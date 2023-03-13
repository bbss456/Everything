import { createApp } from 'vue'
import App from './App.vue'
import { router } from './router/index.js'
import '../main.css'
import vco from "v-click-outside";
import axios from 'axios'

const app = createApp(App)
app.config.globalProperties.$axios = axios; 
app.use(vco)
app.use(router)  // 라우터 사용
app.mount('#app')