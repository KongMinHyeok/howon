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
            <v-col cols="1" style="text-align: center">제목</v-col>
            <v-col cols="11"
              ><v-text-field
                label="제목입력"
                variant="outlined"
                v-model="article.title"
              ></v-text-field
            ></v-col>
          </v-row>
          <v-row>
            <v-col cols="1" style="text-align: center">내용</v-col>
            <v-col cols="11">
              <ckeditor
                :editor="editor"
                v-model="article.content"
                :config="editorConfig"
                style="height: 100%"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="1" style="text-align: center">파일</v-col>
            <v-col cols="11"
              ><v-file-input
                name="file"
                @input="changeFile"
                show-size="100%"
                label="파일을 드래그하여 업로드 가능"
                variant="outlined"
                v-model="article.fname"
                id="file"
                multiple
              ></v-file-input>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="1"></v-col>
            <v-col cols="11"></v-col>
          </v-row>
          <v-sheet style="text-align: center">
            <v-btn color="blue" @click="btnWrite()" class="ml-2">저장</v-btn>
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
import CKEditor from "@ckeditor/ckeditor5-vue";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

const ckeditor = CKEditor.component;
// ✅ :editor
const editor = ClassicEditor;
// ✅ :config
const editorConfig = ref({
  // 설정 옵션
  toolbar: [
    "heading",
    "|",
    "fontBackgroundColor",
    "fontColor",
    "fontSize",
    "bold",
    "italic",
    "|",
    "alignment",
    "bulletedList",
    "numberedList",
    "indent",
    "outdent",
    "|",
    "imageUpload",
    "insertTable",
    "link",
    "|",
    "undo",
    "redo",
  ],
  table: {
    contentToolbar: [
      "tableColumn",
      "tableRow",
      "mergeTableCells",
      "tableProperties",
      "tableCellProperties",
    ],
  },
  image: {
    resize: true,
    toolbar: [
      "imageStyle:alignLeft",
      "imageStyle:alignRight",
      "imageStyle:inline",
      "imageStyle:side",
    ],
  },
});

const router = useRouter();
const userStore = useStore();

const article = reactive({
  uid: null,
  title: null,
  content: null,
});

const dialog = ref(false);

const btnCloseDlg = () => {
  dialog.value = false;
  router.push("/list");
};

const btnCancel = () => {
  router.push("/list");
};

const changeFile = (e) => {
  article.fname = Array.from(e.target.files);

  console.log(article.fileCount);

  const article = reactive({
    uid: null,
    title: null,
    content: null,
    fname: String,
    file: [],
    fileCount: 0,
  });
};

const btnWrite = () => {
  const user = userStore.getters.user;
  article.uid = user.uid;

  const formData = new FormData();
  formData.append("uid", article.uid);
  formData.append("title", article.title);
  formData.append("content", article.content);

  if (article.fname && article.fname.length > 0) {
    for (let i = 0; i < article.fname.length; i++) {
      formData.append("fname", article.fname[i]);
    }
  }
  axios
    .post("http://localhost:8080/Voard/write", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((response) => {
      console.log(article);
      if (response.data > 0) {
        dialog.value = true;
        console.log(article.file);
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
