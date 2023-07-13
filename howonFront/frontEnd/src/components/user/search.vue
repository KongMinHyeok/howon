<template>
  <div id="app">
    <v-app id="list-sample">
      <v-flex sm6 offset-sm3>
        <v-text-field label="Search Data..." v-model="search"> </v-text-field>
        <v-list two-line v-for="(listItem, index) in calData" :key="index">
          <v-list-tile>
            <v-list-tile-content>
              <v-list-tile-title class="text--primary">
                {{ listItem.subject }}
              </v-list-tile-title>
              <v-list-tile-sub-title>
                {{ listItem.desc }}
              </v-list-tile-sub-title>
            </v-list-tile-content>

            <v-list-tile-action>
              <v-icon ripple @click="onEdit(index)"> edit </v-icon>
              <v-icon ripple @click="onRemove(index)"> delete </v-icon>
            </v-list-tile-action>
          </v-list-tile>
          <v-divider></v-divider>
        </v-list>
        <br />
        <v-pagination v-model="curPageNum" :length="numOfPages"> </v-pagination>
      </v-flex>

      <v-layout class="create-data-btn">
        <v-btn fab dark color="indigo" @click="onAdd">
          <v-icon dark> add </v-icon>
        </v-btn>
      </v-layout>

      <v-dialog
        v-model="addDialog"
        persistent
        max-width="500px"
        @keydown.esc="closeDialog"
      >
        <base-modal>
          <div slot="title" class="headline">Add Data</div>
          <v-container slot="content" grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field
                  label="Subject"
                  v-model="newData.subject"
                  required
                >
                </v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field
                  label="Desc"
                  v-model="newData.desc"
                  @keydown.enter="submit"
                  required
                >
                </v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
          <v-btn
            slot="negative-button"
            color="blue darken-1"
            flat
            @click="closeDialog"
          >
            Close
          </v-btn>
          <v-btn
            slot="positive-button"
            color="blue darken-1"
            flat
            @click="submit"
          >
            Save
          </v-btn>
        </base-modal>
      </v-dialog>

      <v-dialog
        v-model="editDialog"
        persistent
        max-width="500px"
        @keydown.esc="closeDialog"
      >
        <base-modal>
          <div slot="title" class="headline">Edit Data</div>
          <v-container slot="content" grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field
                  label="Subject"
                  v-model="selectedData.subject"
                  required
                >
                </v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field
                  label="Desc"
                  v-model="selectedData.desc"
                  required
                  @keydown.enter="editData"
                >
                </v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
          <v-btn
            slot="negative-button"
            color="blue darken-1"
            flat
            @click="closeDialog"
          >
            Close
          </v-btn>
          <v-btn
            slot="positive-button"
            color="blue darken-1"
            flat
            @click="editData"
          >
            Save
          </v-btn>
        </base-modal>
      </v-dialog>

      <v-dialog
        v-model="removeDialog"
        max-width="500px"
        @keydown.esc="removeDialog = false"
        @keydown.enter="removeData"
        persistent
      >
        <base-modal>
          <div slot="title" class="headline">Remove Data</div>
          <v-btn
            slot="negative-button"
            color="blue darken-1"
            flat
            @click="closeDialog"
          >
            Cancel
          </v-btn>
          <v-btn
            slot="positive-button"
            color="blue darken-1"
            flat
            @click="removeData"
          >
            Confirm
          </v-btn>
        </base-modal>
      </v-dialog>
    </v-app>
  </div>
</template>
<script></script>
<style></style>
