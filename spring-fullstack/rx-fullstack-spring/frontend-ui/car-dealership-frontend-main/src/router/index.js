import Vue from 'vue';
import VueRouter from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import store from '../store';

Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { requiresAuth: true }
  },
  {
    path: '/vehicles',
    name: 'vehicles',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/VehiclesView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/sellers',
    name: 'sellers',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/SellersView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/sales',
    name: 'sales',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/SalesView.vue'),
    meta: { requiresAuth: true }
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isAuthenticated) {
      next();
      return;
    }
    next('/login');
  } else {
    next();
  }
});

export default router;
