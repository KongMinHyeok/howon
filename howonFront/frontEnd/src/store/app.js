import axios from "axios";
import { defineStore } from "pinia";

export const useAppStore = defineStore("app", {
  state: () => ({
    user: {},
    articles: [],
    article: { no: 0, title: "", content: "" },
    page: 0,
    no: 0,
  }),
  actions: {
    setUser(user) {
      this.user = user;
    },
    setPage(pg) {
      this.page = pg;
    },
    setNo(no) {
      this.page = no;
    },
    setArticles(pg) {
      const url = "http://localhost:8184/api/list?pg=" + pg;
      axios
        .get(url)
        .then((res) => {
          this.articles = res.data;
        })
        .catch((err) => {
          alert(err.message);
        });
    },
    setArticle(no) {
      const url = `http://localhost:8184/api/${no}`;
      axios
        .get(url)
        .then((res) => {
          this.article = res.data;
        })
        .catch((err) => {
          alert(err.message);
        });
    },
  },
  getters: {
    getUser() {
      return this.user;
    },
    getPg() {
      return this.page;
    },
    getNo() {
      return this.no;
    },
    getArticles() {
      return this.articles;
    },
    getArticle() {
      return this.article;
    },
  },
});