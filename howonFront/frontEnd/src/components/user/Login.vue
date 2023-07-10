<template>
  <v-app>
    <v-app-bar>
      <v-app-bar-title align="center">로그인</v-app-bar-title>
    </v-app-bar>
    <v-main>
      <v-container>
        <v-card class="mx-auto mt-10" max-width="400">
          <v-card-item>
            <v-card-title align="center">로그인</v-card-title>
          </v-card-item>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="8">
                  <v-text-field
                    v-model="user.uid"
                    clearable
                    hide-details="auto"
                    label="아이디"
                  ></v-text-field>
                  <v-text-field
                    v-model="user.pass"
                    clearable
                    hide-details="auto"
                    label="비밀번호"
                  ></v-text-field>
                </v-col>
                <v-col cols="4">
                  <v-btn block height="90" color="primary" @click="btnLogin"
                    >로그인</v-btn
                  >
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="btnRegister">회원가입</v-btn>
          </v-card-actions>
        </v-card>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { useRouter } from "vue-router";
import axios from "axios";
import { reactive } from "vue";
import { useStore } from "vuex";

const router = useRouter();
const userStore = useStore();

const user = reactive({
  uid: null,
  pass: null,
});

const btnLogin = () => {
  axios
    .post("http://localhost:8080/howon/user/login", user)
    .then((response) => {
      console.log(response);

      const accessToken = response.data.accessToken;
      const user = response.data.user;

      localStorage.setItem("accessToken", accessToken);
      userStore.dispatch("setUser", user);

      router.push("/list");
    })
    .catch((error) => {
      console.log(error);
    });
};
const btnRegister = () => {
  router.push("/user/register");
};
</script>

<style scoped></style>
