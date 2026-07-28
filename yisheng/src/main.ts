import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { ElMessage } from 'element-plus'
import Businesserror from './config/BusinessError.ts'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.config.errorHandler = (error: unknown, instance, info) => {
    if (error instanceof Businesserror) {
        ElMessage.error(error.msg || "业务异常");
    } else if (error instanceof Error) {
        ElMessage.error(error.message || "系统异常");
    } else {
        ElMessage.error("未知错误");
    }
}

app.mount('#app')
