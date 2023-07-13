<template>
  <v-app>
    <v-app-bar :elevation="1">
      <v-app-bar-title>공지사항</v-app-bar-title>
      <v-btn @click="btnLogout">공지사항 > 공지사항 > 입력</v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <v-sheet max-width="100%" class="mx-auto">
          <v-row class="mt-4">
            <v-col cols="1">제목</v-col>
            <v-col cols="11"
              ><v-text-field
                label="제목입력"
                variant="outlined"
                v-model="article.title"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-row>
            <v-col cols="1">내용</v-col>
            <v-col cols="11">
              <v-textarea
                label="내용입력"
                variant="outlined"
                rows="12"
                v-model="article.content"
              ></v-textarea>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="1">파일</v-col>
            <v-col cols="11"
              ><v-file-input
                show-size="100%"
                label="파일을 드래그하여 업로드 가능"
                variant="outlined"
                v-model="article.file"
              ></v-file-input
            ></v-col>
          </v-row>
          <v-row>
            <v-col cols="1"></v-col>
            <v-col cols="11"
              ><v-btn @click="btnfile" style="top: -30px"
                >파일 선택</v-btn
              ></v-col
            >
          </v-row>
          <v-sheet style="text-align: center">
            <v-btn color="blue" @click="btnWrite" class="ml-2">저장</v-btn>
            <v-btn color="black" @click="btnCancel">취소</v-btn>
          </v-sheet>
        </v-sheet>
        <v-dialog v-model="dialog" width="auto">
          <v-card>
            <v-toolbar color="primary" title="글 등록 확인"></v-toolbar>
            <v-card-text>작성한 글이 등록 되었습니다.</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="btnCloseDlg">확인</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>
    </v-main>
  </v-app>
</template>
<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";

const router = useRouter();
const userStore = useStore();

const article = reactive({
  title: null,
  content: null,
  file: null,
});

const dialog = ref(false);

const btnCloseDlg = () => {
  dialog.value = false;
  router.push("/list");
};

const btnCancel = () => {
  router.push("/list");
};
const btnWrite = () => {
  const user = userStore.getters.user;
  article.uid = user.uid;

  axios
    .post("http://localhost:8080/Voard/write", article)
    .then((response) => {
      console.log(response);
      if (response.data > 0) {
        dialog.value = true;
        //router.push("/list");
      }
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>
<style scoped>
.v-app-bar-title {
  font-weight: bold;
  font-size: 30px;
  padding-left: 5%;
}
</style>
