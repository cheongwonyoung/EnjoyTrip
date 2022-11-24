<template>
  <div class="mt-5 mb-5">
    <b-container class="bv-example-row mt-3">
      <b-row>
        <b-col style="position: relative; padding: 0px">
          <b-alert show
            ><h2 style="padding-top: 5px">일정 공유 목록</h2>
            <b-button
              variant="outline-dark"
              @click="moveWrite()"
              v-if="userInfo"
              style="position: absolute; right: 10px; top: 20px"
              >일정 공유하기</b-button
            ></b-alert
          >
        </b-col>
      </b-row>
    </b-container>
    <div>
      <b-card-group columns>
        <div v-for="(article, idx) in articles" :key="idx">
          <b-card
            @click="viewArticle(article.planId)"
            v-bind:img-src="article.thumbNail"
            v-bind:title="article.title"
            img-alt="Image"
            img-width="350"
            img-height="250"
            img-top
          >
            <!-- <b-card-text> {{ article.hit }} </b-card-text> -->
            <template #footer>
              <small class="text-muted">조회수 : {{ article.hit }}</small>
            </template>
          </b-card>
        </div>
      </b-card-group>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "BoardList",
  data() {
    return {
      articles: [],
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "boardwrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "boardview",
        params: { planid: article },
      });
    },
  },
  created() {
    // 게시글 목록.
    http.get("boardcon/list").then(({ data }) => {
      console.log(data);
      this.articles = data;
    });
  },
};
</script>

<style scope>
.card-body {
  height: 70px;
}
</style>
