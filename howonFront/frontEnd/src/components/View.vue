<template>
  <v-app>
    <v-main>
      <v-app-bar>
        <v-app-bar-title>공지사항</v-app-bar-title>
      </v-app-bar>
      <v-container>
        <v-sheet max-width="800" class="mx-auto mt-16">
          <v-input></v-input>
          <v-card-text>
            <v-card>
              <v-card-item>
                <v-card-title>{{ article.title }}</v-card-title>
                <v-card-subtitle>글쓴이 : {{ article.uid }}</v-card-subtitle>
                <v-card-subtitle>작성날짜 : {{ article.rdate }}</v-card-subtitle
                ><v-card-subtitle
                  >작성날짜 : {{ article.rdate }}</v-card-subtitle
                >
              </v-card-item>
              <v-card-text>
                {{ article.content }}
              </v-card-text>
            </v-card>
          </v-card-text>
          <v-sheet>
            <v-row>
              <v-col cols="6">
                <v-btn color="indigo" @click="btnList">목록</v-btn>
              </v-col>
              <v-col cols="6" class="text-end">
                <v-btn color="warning" class="mr-5" @click="btnModify"
                  >수정</v-btn
                >
                <v-btn color="error" @click="btnDelete(article.no)">삭제</v-btn>
              </v-col>
            </v-row>
          </v-sheet>
        </v-sheet>
      </v-container>
    </v-main>
  </v-app>
</template>
<script setup>
import { useAppStore } from "@/store/app";
import { defineProps } from "vue";
import axios from "axios";
import { reactive } from "vue";
import { onBeforeMount } from "vue";
import { useRouter } from "vue-router";

const usersStore = useAppStore();
const router = useRouter();

const props = defineProps({
  no: String,
  title: String,
  content: String,
  uid: String,
  rdate: String,
});

const btnList = () => {
  router.push("/list");
};
const btnModify = (article) => {
  router.push({ name: "Modify", params: article });
};

const btnDelete = (no) => {
  axios
    .delete("http://localhost:8080/Voard/View/" + { params: no })
    .then((res) => {
      console.log(res);
      console.log("delete no:" + article.no);
      router.push("/list");
    })
    .catch((err) => {
      alert(err.message);
      console.log("delete err no:" + article.no);
    });
};

const article = reactive({
  no: props.no,
  title: props.title,
  content: props.content,
  uid: props.uid,
  nick: props.nick,
  rdate: props.rdate,
});

onBeforeMount(() => {
  console.log(usersStore.getArticle);
  if (usersStore.getArticle == null) {
    const no = localStorage.getItem("no");
    const loadArticle = usersStore.setArticle(no);
    article.title = loadArticle.title;
    article.content = loadArticle.content;
  }
});
</script>
<style scoped></style>
