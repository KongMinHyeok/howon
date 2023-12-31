import Login from "../components/user/Login.vue";
import Register from "../components/user/Register.vue";
import search from "../components/user/search.vue";
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
    { path: "/user/search", name: "search", component: search },
    { path: "/List", name: "List", component: List },
    {
      path: "/View/:no/:title/:content/:rdate/:uid",
      name: "View",
      component: View,
      props: true,
    },
    {
      path: "/Modify/:no/:title/:content/:rdate/:uid",
      name: "Modify",
      component: Modify,
      props: true,
    },
    { path: "/Write", name: "Write", component: Write },
  ],
});

export default router;
