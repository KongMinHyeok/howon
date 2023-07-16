<template>
  <v-app>
    <v-app-bar :elevation="1">
      <v-app-bar-title></v-app-bar-title>
      <p>
        {{ user?.uid }}님 반갑습니다.
        <v-btn @click="btnLogout">로그아웃</v-btn>
      </p>
    </v-app-bar>
    <v-main>
      <v-row>
        <v-col cols="3">
          <v-sheet class="vcardtitle">
            <v-card-text align="center">공지사항</v-card-text>
          </v-sheet>
        </v-col>
      </v-row>
    </v-main>
    <v-row style="flex: 0">
      <v-col cols="6">
        <v-sheet> </v-sheet>
      </v-col>
      <v-col cols="6">
        <v-sheet width="70%" class="mx-auto">
          <v-card>
            <v-text-field
              v-model="state.searchKeyword"
              density="compact"
              variant="solo"
              label="검색어를 입력하세요"
              append-inner-icon="mdi-magnify"
              single-line
              hide-details
              @input="searchArticles"
            ></v-text-field>
          </v-card>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row>
      <v-container width="100%">
        <v-sheet width="100%" class="">
          <v-table max-width="100%">
            <thead>
              <tr>
                <th class="text-center">번호</th>
                <th width="1200" class="text-center">제목</th>
                <th class="text-center">작성자</th>
                <th class="text-center">등록일</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(article, index) in state.data.articles"
                :key="article.no"
              >
                <td class="text-center">{{ state.pageStartNum - index }}</td>
                <td class="text-left" @click="btnView(article, user)">
                  {{ article.title }}
                </td>
                <td class="text-center">{{ article.uid }}</td>
                <td class="text-center">
                  {{ article.rdate }}
                </td>
              </tr>
            </tbody>
          </v-table>
          <v-pagination
            :length="state.lastPageNum"
            :total-visible="5"
            rounded="0"
            v-model="page"
            @click="pageHandler"
            @next="pageHandler"
            @prev="pageHandler"
          ></v-pagination>
          <v-sheet class="text-right pt06">
            <v-btn color="black" @click="btnWrite">글쓰기</v-btn>
            <v-btn color="black" @click="btnWrite">처음으로</v-btn>
          </v-sheet>
        </v-sheet>
      </v-container>
    </v-row>
  </v-app>
</template>

<script setup>
import { useAppStore } from "@/store/app";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ref, reactive, computed, onBeforeMount } from "vue";
import axios from "axios";

const usersStore = useAppStore();
const router = useRouter();
const userStore = useStore();

const user = computed(() => userStore.getters.user);

const state = reactive({
  data: {},
  pageStartNum: 0,
  lastPageNum: 0,
  searchKeyword: "",
});
const page = ref(1);

const btnLogout = () => {
  localStorage.removeItem("accessToken");
  router.push("/user/login");
};

const btnWrite = () => {
  router.push("/write");
};

const pageHandler = () => {
  getArticles(page.value);
};

const btnView = (article, user) => {
  router.push({ name: "View", params: article, user });
  console.log(article);
};

const getArticles = (pg) => {
  axios
    .get("http://localhost:8080/Voard/list?pg=" + pg)
    .then((response) => {
      console.log(response);
      const data = response.data;
      state.data = data;
      state.pageStartNum = data.pageStartNum;
      state.lastPageNum = data.lastPageNum;
    })
    .catch((error) => {
      console.log(error);
    });
};
onBeforeMount(() => {
  getArticles(1);
});

const searchArticles = () => {
  const searchUrl = `http://localhost:8080/Voard/list?pg=${page.value}&keyword=${state.searchKeyword}`;
  axios
    .get(searchUrl)
    .then((response) => {
      console.log(response);
      const data = response.data;
      state.data = data;
      state.pageStartNum = data.pageStartNum;
      state.lastPageNum = data.lastPageNum;
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>
<style scoped>
.v-table {
  border-top: 3px solid rgb(0, 140, 255);
}

.v-sheet.vcardtitle {
  border-top: 3px solid rgb(0, 140, 255);
}
.v-card-text {
  font-weight: bold;
  font-size: 30px;
  color: rgb(0, 140, 255);
}
.v-main {
  flex: 0;
}
</style>
