import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'ceshixiangqing',
      component: () => import('../views/ceshixiangqing.vue'),
    },
    {
      path: '/yunxing',
      name: 'yunxingxiangqing',
      component: () => import('../views/yunxingxiangqing.vue'),
    },
  ],
})

export default router
