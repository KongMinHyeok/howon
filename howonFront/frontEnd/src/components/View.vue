<template>
  <v-app>
    <v-main>
      <v-app-bar :elevation="1">
        <v-app-bar-title style="padding-left: 2%">공지사항</v-app-bar-title>
        <p style="font-size: 13px; margin-top: 30px; padding-right: 3%">
          공지사항 > 공지사항
        </p>
      </v-app-bar>
      <v-container>
        <v-row
          style="
            margin-top: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid rgb(0, 140, 255);
          "
          ><h2>{{ article.title }}</h2></v-row
        >

        <v-row
          style="
            padding: 10px 0px 10px;
            border-bottom: 1px solid rgb(0, 140, 255);
          "
        >
          <v-col cols="6"
            ><p>{{ article.uid }}</p></v-col
          >
          <v-col cols="6"
            ><p style="width: 100%; text-align: right">
              {{ article.rdate }}
            </p></v-col
          >
        </v-row>
        <v-row style="border-bottom: 1px solid rgb(0, 140, 255)">
          <p
            style="padding-top: 10px; height: 500px"
            v-html="article.content"
          ></p>
        </v-row>
        <v-row>
          <h2>첨부파일</h2>
          <v-col v-if="files.fileList.length > 0">
            <p v-for="file in files.fileList">{{ file.oriName }}</p>
          </v-col>
        </v-row>
        <v-row class="btn">
          <v-col>
            <v-col cols="12" class="text-end">
              <v-row
                style="display: contents;, right: 1px"
                v-if="article.uid == user.uid"
              >
                <v-dialog v-model="dialog" persistent width="auto">
                  <template v-slot:activator="{ props }">
                    <v-btn color="blue" v-bind="props"> 수정 </v-btn>
                  </template>
                  <v-card>
                    <v-card-title class="text-h5"> 수정 </v-card-title>
                    <v-card-text>수정페이지로 넘어 가시겠습니까?</v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="green-darken-1"
                        variant="text"
                        @click="dialog = false"
                      >
                        취소하기
                      </v-btn>
                      <v-btn
                        color="green-darken-1"
                        variant="text"
                        @click="btnModify(article)"
                      >
                        수정하기
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <v-btn color="error" @click="btnDelete(article)">삭제</v-btn>
              </v-row>
              <v-btn color="indigo" @click="btnList">목록</v-btn>
            </v-col>
          </v-col>
        </v-row>
        <v-text></v-text>
        <v-sheet max-width="800" class="mx-auto mt-16">
          <v-input></v-input>
          <v-sheet> </v-sheet>
        </v-sheet>
      </v-container>
    </v-main>
  </v-app>
</template>
<script setup>
import { ref, defineProps, reactive, onBeforeMount, computed } from "vue";
import { useStore } from "vuex";
import { useAppStore } from "@/store/app";
import axios from "axios";
import { useRouter } from "vue-router";

const userStore = useStore();
const dialog = ref(false);
const user = computed(() => userStore.getters.user);
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

const btnDelete = (article) => {
  axios
    .delete("http://localhost:8080/Voard/view?no=" + article.no)
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

const file = reactive({
  fno: 0,
  parent: 0,
  newName: "",
  oriName: "",
  download: 0,
  rdate: "",
});

const files = ref([]);

onBeforeMount(() => {
  axios
    .get("http://localhost:8080/Voard/view?no=" + article.no)
    .then((response) => {
      const responseData = response.data;
      article.value = responseData;
      files.value = responseData;
      console.log(files.value);
      console.log(article);
    })
    .catch((error) => {
      console.log(error);
    });
});
</script>
<style scoped>
v-app-bar {
  width: 90%;
}
.v-app-bar-title {
  font-weight: bold;
  font-size: 28px;
}
v-title {
  font-weight: bold;
  font-size: 20px;
}
.file {
  font-size: 20px;
}
</style>
