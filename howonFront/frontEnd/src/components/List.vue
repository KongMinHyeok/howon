<template>
  <v-app>
    <v-app-bar>
      <v-app-bar-title></v-app-bar-title>
      <p>
        {{ user?.nick }}님 반갑습니다.
        <v-btn @click="btnLogout">로그아웃</v-btn>
      </p>
    </v-app-bar>
    <v-main>
      <v-row rows="2">
        <v-col>
          <v-card>
            <v-card-text align="center">공지사항</v-card-text>
          </v-card>
        </v-col>
        <v-col>
          <v-card> </v-card>
        </v-col>
        <v-col>
          <v-card> </v-card>
        </v-col>
        <v-col>
          <v-card> </v-card>
        </v-col>
        <v-col>
          <v-card> </v-card>
        </v-col>
      </v-row>
    </v-main>
    <v-sheet width="300">
      <v-card-text>
        <v-text-field
          :loading="loading"
          density="compact"
          variant="solo"
          label="검색어를 입력하세요"
          append-inner-icon="mdi-magnify"
          single-line
          hide-details
          @click:append-inner="onClick"
        ></v-text-field>
      </v-card-text>
    </v-sheet>
    <v-container>
      <v-sheet max-width="100%" class="">
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
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
            <tr>
              <td class="text-center">1</td>
              <td class="text-left">설교경강집</td>
              <td class="text-center">강원규</td>
              <td class="text-center">2021-08-19</td>
            </tr>
          </tbody>
        </v-table>
        <v-pagination
          :container-class="'pagination'"
          :length="lastPageNum"
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
  </v-app>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ref, reactive, computed, onBeforeMount } from "vue";
import axios from "axios";

const router = useRouter();
const userStore = useStore();

const user = computed(() => userStore.getters.user);

const state = reactive({
  data: {},
  pageStartNum: 0,
  lastPageNum: 0,
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

const getArticles = (pg) => {
  axios
    .get("http://localhost:8080/howon/list?pg=" + pg)
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
</script>
<style scoped>
.v-table {
  border-top: 2px solid blue;
}
.pagination li {
  border: 1px solid gray;
}
</style>
