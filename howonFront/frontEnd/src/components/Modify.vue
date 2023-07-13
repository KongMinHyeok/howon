<template>
  <v-app>
    <v-app-bar>
      <v-app-bar-title>공지사항(수정)</v-app-bar-title>
      <p>
        {{ user?.uid }}님 반갑습니다.
        <v-btn @click="btnLogout">로그아웃</v-btn>
      </p>
    </v-app-bar>
    <v-main>
      <v-container>
        <v-sheet max-width="800" class="mx-auto mt-10">
          <v-form>
            <v-card>
              <v-card-item>
                <v-card-title>글 수정하기</v-card-title>
                <v-card-subtitle>글쓴이 : {{ article.uid }}</v-card-subtitle>
                <v-input type="hidden" v-model="article.no"></v-input>
              </v-card-item>
              <v-card-text>
                <v-text-field
                  label="글제목 입력"
                  variant="outlined"
                  density="compact"
                  v-model="article.title"
                ></v-text-field>
                <v-textarea
                  label="글내용 입력"
                  variant="outlined"
                  rows="10"
                  v-model="article.content"
                ></v-textarea>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red" @click="btnCancel">취소하기</v-btn>
                <v-btn color="success" @click="btnModify">수정하기</v-btn>
              </v-card-actions>
            </v-card>
          </v-form>
        </v-sheet>
      </v-container>
    </v-main>
    <v-footer app theme="dark"> copyright &copy;Voard2 v1.0 </v-footer>
  </v-app>
</template>
<script setup>
import axios from "axios";
import { reactive, computed } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
const userStore = useStore();
const router = useRouter();
const user = computed(() => userStore.getters.user);
const props = defineProps({
  no: String,
  title: String,
  content: String,
  uid: String,
  nick: String,
  rdate: String,
});
const article = reactive({
  no: props.no,
  title: props.title,
  content: props.content,
  uid: props.uid,
  nick: props.nick,
  rdate: props.rdate,
});
const btnLogout = () => {
  localStorage.removeItem("accessToken");
  router.push("/user/login");
};
const btnCancel = () => {
  router.push({ name: "View", params: article });
};
const btnModify = () => {
  axios
    .put("http://localhost:8080/Voard/modify", article)
    .then((response) => {
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
  router.push({ name: "View", params: article });
};
</script>
<style scoped></style>
