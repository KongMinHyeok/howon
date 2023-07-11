import Login from "../components/user/Login.vue";
import Register from "../components/user/Register.vue";
import layout from "../components/user/layout.vue";
import List from "../components/List.vue";
import View from "../components/View.vue";
import Modify from "../components/Modify.vue";
import Write from "../components/Write.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/user/Login", name: "Login", component: Login },
    { path: "/user/Register", name: "Register", component: Register },
    { path: "/user/layout", name: "layout", component: layout },
    { path: "/List", name: "List", component: List },
    { path: "/View", name: "View", component: View },
    { path: "/Modify", name: "Modify", component: Modify },
    { path: "/Write", name: "Write", component: Write },
  ],
});

export default router;
