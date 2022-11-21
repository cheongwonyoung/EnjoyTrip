<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>일정 공유 목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <div>
        <h2>{{ article.title }}</h2>
        <div>
          <p>
            <span>이형석</span> |
            <span>{{ article.registerDate }}</span>
          </p>
        </div>
      </div>
      <div>조회 : {{ article.hit + 1 }}</div>
    </b-row>

    <b-card-group columns>
      <div v-for="(content, idx) in contentList" :key="idx">
        <b-card v-b-modal="`modal-${idx}`" v-bind:title="content.contentTitle" v-bind:img-src="content.contentImg" img-alt="Image" img-top>
          <b-card-text> {{ content.contentDesc }} </b-card-text>
          <!-- <template #footer>
              <small class="text-muted">Last updated 3 mins ago</small>
            </template> -->
        </b-card>

        <!-- <b-modal :id="'modal-' + idx">
          <h1>여행지 정보</h1>
          <b-card v-bind:img-src="portfolio.firstImage" v-bind:title="portfolio.title" img-alt="Image" img-top> </b-card>
          <b-card-text> {{ portfolio.overview }} </b-card-text>
        </b-modal> -->
      </div>
    </b-card-group>

    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="primary" @click="moveList()">목록보기</b-button>
        <b-button variant="danger" @click="deleteView()">삭제하기</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>
``
<script>
import http from "@/api/http";
export default {
  name: "BoardView",
  data() {
    return {
      article: {},
      contentList: [],
    };
  },
  methods: {
    moveList() {
      this.$router.push({ name: "boardlist" });
    },
    deleteView() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        // 확인(예) 버튼 클릭 시 이벤트
        console.log("article");
        console.log(this.article.planId);

        http.delete("/boardcon/list/" + this.article.planId).then((response) => {
          console.log("delete");
          console.log(response);
          this.moveList();
        });
      }
    },
  },
  created() {
    http.get("/boardcon/view/" + this.$route.params.planid).then(({ data }) => {
      console.log(data);
      this.article = data;
      this.contentList = data.contentList;
      console.log("!!!");
      console.log(this.contentList);
    });
  },
};
</script>

<style></style>
