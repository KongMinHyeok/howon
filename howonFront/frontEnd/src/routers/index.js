import Login from "../components/user/Login.vue";
import Register from "../components/user/Register.vue";
import List from "../components/List.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/user/Login", name: "Login", component: Login },
    { path: "/user/Register", name: "Register", component: Register },
    { path: "/List", name: "List", component: List },
  ],
});

export default router;
