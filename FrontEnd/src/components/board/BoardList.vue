<template>
  <div>
    <b-container class="bv-example-row mt-3">
      <b-row>
        <b-col>
          <b-alert show><h3>일정 공유 목록</h3></b-alert>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-right">
          <b-button variant="outline-primary" @click="moveWrite()">일정 공유하기</b-button>
        </b-col>
      </b-row>
    </b-container>
    <div>
      <b-card-group columns>
        <div v-for="(article, idx) in articles" :key="idx">
          <b-card @click="viewArticle(article.planId)" v-bind:img-src="article.thumbNail" v-bind:title="article.title" img-alt="Image" img-top>
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

export default {
  name: "BoardList",
  data() {
    return {
      articles: [],
    };
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

<style></style>
