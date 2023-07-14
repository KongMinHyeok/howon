<template>
  <h4>Grids</h4>
  <div class="text-center">
    <v-dialog v-model="dialog" width="auto">
      <template v-slot:activator="{ props }">
        <v-btn color="primary" v-bind="props"> Open Dialog </v-btn>
      </template>

      <v-card>
        <v-card-text>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua.
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" block @click="dialog = false"
            >Close Dialog</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
  <v-container fluid>
    <v-row>
      <v-col cols="12" class="border text-center">12</v-col>
    </v-row>
    <v-row class="mt-4">
      <v-col cols="6" class="border text-center">6</v-col>
      <v-col cols="6" class="border text-center">6</v-col>
    </v-row>
    <v-row class="mt-4">
      <v-col cols="4" class="border text-center">4</v-col>
      <v-col cols="4" class="border text-center">4</v-col>
      <v-col cols="4" class="border text-center">4</v-col>
    </v-row>
    <v-row class="mt-4">
      <v-col cols="3" class="border text-center">3</v-col>
      <v-col cols="3" class="border text-center">3</v-col>
      <v-col cols="3" class="border text-center">3</v-col>
      <v-col cols="3" class="border text-center">3</v-col>
    </v-row>
    <v-row class="mt-4">
      <v-col cols="5" class="border text-center">5</v-col>
      <v-col cols="5" class="border text-center">5</v-col>
      <v-col cols="2" class="border text-center">2</v-col>
    </v-row>
    <v-row class="mt-4">
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
      <v-col cols="1" class="border text-center">1</v-col>
    </v-row>

    <v-row class="mt-4">
      <v-col md="4" lg="12" class="border text-center">sm1</v-col>
      <v-col md="4" lg="12" class="border text-center">sm2</v-col>
      <v-col md="4" lg="12" class="border text-center">sm3</v-col>
    </v-row>
  </v-container>

  <h4>반응형 레이아웃</h4>
  <v-container fluid>
    <v-row>
      <v-col cols="12" md="6" lg="3" class="border text-center">column1</v-col>
      <v-col cols="12" md="6" lg="3" class="border text-center">column2</v-col>
      <v-col cols="12" md="6" lg="3" class="border text-center">column3</v-col>
      <v-col cols="12" md="6" lg="3" class="border text-center">column4</v-col>
    </v-row>
  </v-container>
</template>
<script setup>
import { ref } from "vue";

const dialog = ref(false);
</script>
<style scoped></style>
