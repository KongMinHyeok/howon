<template>
  <v-app>
    <v-app-bar>
      <v-app-bar-title align="center">회원가입</v-app-bar-title>
    </v-app-bar>
    <v-main>
      <v-container>
        <v-sheet max-width="800" class="mx-auto mt-10">
          <v-card class="mb-6">
            <v-card-item>
              <v-card-title align="center">회원가입</v-card-title>
            </v-card-item>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="6">
                    <v-text-field
                      v-model="user.uid"
                      clearable
                      hide-details="auto"
                      label="아이디"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="6">
                    <v-btn
                      :loading="loading"
                      color="success"
                      @click="btnCheckUid"
                      >중복확인</v-btn
                    >
                    <v-chip v-if="rsChip1" class="ma-2" color="red">
                      이미 사용중인 아이디 입니다.
                    </v-chip>
                    <v-chip v-if="rsChip2" class="ma-2" color="green">
                      사용 가능한 아이디 입니다.
                    </v-chip>
                  </v-col>

                  <v-col cols="6">
                    <v-text-field
                      v-model="user.pass1"
                      clearable
                      hide-details="auto"
                      label="비밀번호"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="6"></v-col>
                  <v-col cols="6">
                    <v-text-field
                      v-model="user.pass2"
                      clearable
                      hide-details="auto"
                      label="비밀번호 확인"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="6">
                    <v-chip v-if="rsChip3" class="ma-2" color="green">
                      비밀번호가 일치합니다
                    </v-chip>
                    <v-chip v-if="rsChip4" class="ma-2" color="red">
                      비밀번호가 일치하지 않습니다.
                    </v-chip>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-sheet max-width="800" class="mx-auto text-center">
              <v-btn @click="btnCancel">취소</v-btn>
              <v-btn class="ml-2" color="primary" @click="btnRegister"
                >등록</v-btn
              >
            </v-sheet>
            <v-spacer></v-spacer>
          </v-card>
        </v-sheet>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();

const user = reactive({
  uid: null,
  pass1: null,
  pass2: null,
});

const rsChip1 = ref(false);
const rsChip2 = ref(false);
const rsChip3 = ref(false);
const rsChip4 = ref(false);
const loading = ref(false);

const btnCheckUid = () => {
  loading.value = true;

  axios
    .get("http://localhost:8080/Voard/user/countUid", {
      params: { uid: user.uid },
    })
    .then((response) => {
      setTimeout(() => {
        loading.value = false;

        if (response.data > 0) {
          rsChip1.value = true;
          rsChip2.value = false;
        } else {
          rsChip1.value = false;
          rsChip2.value = true;
        }
      }, 500);

      console.log(response);
    })
    .catch((error) => {
      console.log(error);
    });
};

const btnCancel = () => {
  router.push("/user/login");
};

const btnRegister = () => {
  axios
    .post("http://localhost:8080/Voard/user/register", user)
    .then((response) => {
      console.log(response);
      router.push("/user/login");
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>

<style scoped></style>
