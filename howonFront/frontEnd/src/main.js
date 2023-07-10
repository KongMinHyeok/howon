import { createApp } from "vue";
import App from "./App.vue";
import userStore from "./store/user.js";
import router from "./routers/index.js";
import { registerPlugins } from "@/plugins";

const app = createApp(App);

registerPlugins(app);

app.use(router);
app.use(userStore);
app.mount("#app");
