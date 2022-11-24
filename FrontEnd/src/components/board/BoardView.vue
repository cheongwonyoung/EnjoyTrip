<template>
  <div class="mt-5 mb-5">
    <b-container class="bv-example-row mt-5">
      <b-row>
        <b-col style="position: relative; padding: 0px">
          <b-alert show
            ><h2 style="padding-top: 5px">"{{ userName }}"의 여행 계획</h2>
            <b-button
              variant="outline-dark"
              @click="movePage()"
              style="position: absolute; right: 10px; top: 20px"
              >목록으로 돌아가기</b-button
            ></b-alert
          >
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="10" style="text-align: left; padding: 0px">
          <h2>{{ article.title }}</h2>
          <div>
            <p>
              <span>{{ userName }}</span> |
              <span>{{ article.registerDate }}</span>
            </p>
          </div>
        </b-col>

        <b-col cols="2" style="padding: 0px"
          >조회 : {{ article.hit + 1 }}</b-col
        >
      </b-row>

      <b-card-group columns>
        <div v-for="(content, idx) in contentList" :key="idx">
          <b-card
            v-b-modal="`modal-${idx}`"
            v-bind:title="content.contentTitle"
            v-bind:img-src="content.contentImg"
            img-alt="Image"
            img-width="350"
            img-height="250"
            img-top
          >
            <b-card-text>
              {{ content.contentDesc }}
            </b-card-text>
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

      <b-row class="mb-1" v-if="userInfo.userId == article.userId">
        <b-col class="text-right">
          <b-button variant="danger" @click="deleteView()">삭제하기</b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
``
<script>
import http from "@/api/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "BoardView",
  data() {
    return {
      article: {},
      contentList: [],
      userId: "",
      userName: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
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

        http
          .delete("/boardcon/list/" + this.article.planId)
          .then((response) => {
            console.log("delete");
            console.log(response);
            this.moveList();
          });
      }
    },
    movePage() {
      this.$router.push({ name: "boardlist" });
    },
  },
  async created() {
    await http
      .get("/boardcon/view/" + this.$route.params.planid)
      .then(({ data }) => {
        console.log(data);
        this.article = data;
        this.contentList = data.contentList;
        console.log("!!!");
        console.log(this.contentList);
      });

    await http.get("/user/infoprofile/" + this.article.userId).then((data) => {
      this.userName = data.data.userInfo.userName;
      this.userId = data.data.userInfo.userId;
      console.log("유저 이름 잘 찍혀? ");
      console.log(this.userName);
      console.log(data.data.userInfo.userName);
    });
  },
};
</script>

<style scope>
.card-body {
  margin: auto;
  width: 300px;
  height: 100px;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
