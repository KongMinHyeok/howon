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
                    type="password"
                    v-model="user.pass"
                    clearable
                    hide-details="auto"
                    label="비밀번호"
                  ></v-text-field>
                </v-col>
                <v-col cols="4">
                  <v-btn color="primary" @click="btnLogin" block height="90">
                    로그인
                  </v-btn>
                  <v-dialog v-model="dialog1" width="auto">
                    <v-card>
                      <v-card-text> 로그인 성공 </v-card-text>
                      <v-card-actions>
                        <v-btn color="primary" @click="btntrue"
                          >Close Dialog</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                  <v-dialog v-model="dialog2" width="auto">
                    <v-card>
                      <v-card-text> 로그인 실패 </v-card-text>
                      <v-card-actions>
                        <v-btn color="primary" @click="btnfalse"
                          >Close Dialog</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
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
import { ref } from "vue";

const router = useRouter();
const userStore = useStore();

const dialog1 = ref(false);
const dialog2 = ref(false);

const btnfalse = () => {
  dialog2.value = false;
};

const btntrue = () => {
  dialog1.value = false;
  router.push("/list");
};

const user = reactive({
  uid: null,
  pass: null,
});

const btnLogin = () => {
  axios
    .post("http://localhost:8080/Voard/user/login", user)
    .then((response) => {
      console.log(response);

      const accessToken = response.data.accessToken;
      const user = response.data.user;

      localStorage.setItem("accessToken", accessToken);
      userStore.dispatch("setUser", user);

      dialog1.value = true;
      //router.push("/list");
    })
    .catch((error) => {
      dialog2.value = true;
      console.log(error);
    });
};
const btnRegister = () => {
  router.push("/user/register");
};
</script>

<style scoped></style>
