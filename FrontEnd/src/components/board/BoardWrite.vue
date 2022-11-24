<template>
  <div class="mt-5 mb-5">
    <b-container class="bv-example-row mt-3">
      <b-row>
        <b-col style="position: relative; padding: 0px">
          <b-alert show
            ><h2 style="padding-top: 5px">여행지 일정 공유</h2>
            <b-button
              variant="outline-dark"
              @click="movePage()"
              v-if="userInfo"
              style="position: absolute; right: 10px; top: 20px"
              >목록으로 돌아가기</b-button
            ></b-alert
          >
        </b-col>
      </b-row>
    </b-container>

    <b-form v-if="show" class="mb-3">
      <b-row>
        <b-col cols="7">
          <kakao-map
            :recoList="recoList"
            style="width: 95%; height: 450px; padding-left: 0px"
          ></kakao-map>
        </b-col>
        <b-col cols="5" style="padding: 0" class="float-right">
          <b-row style="width: 100%" class="mt-5">
            <b-form-group
              id="input-group-1"
              label="나만의 여행지는"
              label-for="input-1"
              style="width: 100%; font-size: 25px"
            >
              <b-form-input
                id="input-1"
                v-model="form.title"
                type="name"
                placeholder="제목 입력"
                required
              ></b-form-input>
            </b-form-group>
          </b-row>
          <b-row style="width: 100%; margin-top: 70px">
            <b-form-group
              id="input-group-2"
              label="여기로 정할래요"
              label-for="input-2"
              style="width: 100%; font-size: 25px"
            >
              <b-row>
                <b-col cols="9"
                  ><b-form-input
                    id="input-2"
                    v-model="word"
                    @keyup.enter="recommend"
                    style="width: 100%"
                    placeholder="검색어 입력"
                    required
                  ></b-form-input
                ></b-col>
                <b-col cols="3">
                  <b-button
                    variant="dark"
                    @click="recommend"
                    style="width: 100%"
                    >검색</b-button
                  >
                </b-col>
              </b-row>
            </b-form-group>
          </b-row>
        </b-col>
      </b-row>
    </b-form>

    <carousel :perPage="4" :navigationEnabled="true" :paginationEnabled="false">
      <slide v-for="(reco, index) in recoList" :key="index">
        <b-card
          :title="reco.title"
          :img-src="reco.firstImage"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem"
          class="mb-2"
        >
          <b-card-text>
            {{ reco.addr1 }}
          </b-card-text>
          <b-button
            variant="info"
            class="btn-circle-sm btn-select"
            @click="addPlanList(reco)"
          >
            ✔
          </b-button>
        </b-card>
      </slide>
    </carousel>

    <b-container fluid>
      <b-row
        class="my-1 mt-3"
        v-for="(select, idx) in selected"
        :key="idx"
        style="line-height: 70px"
      >
        <b-col sm="3" style="padding: 0">
          <div>
            <b-img
              v-bind:src="select.image"
              class="img-under"
              fluid
              alt="Responsive image"
            ></b-img>
          </div>
        </b-col>
        <b-col sm="9" style="padding-right: 0">
          <label for="input-none" style="font-size: 25px">{{
            select.title
          }}</label>
          <b-form-input
            v-model="select.text"
            id="input-none"
            :state="null"
            placeholder="글 작성"
          ></b-form-input>
        </b-col>
      </b-row>
    </b-container>

    <b-button class="mt-3" variant="dark" @click="write" style="width: 100%"
      >등록</b-button
    >
  </div>
</template>

<script>
import KakaoMap from "../KakaoMap.vue";
import http from "@/api/http";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "BoardWrite",

  data() {
    return {
      selected: [],
      recoList: [],
      form: {
        title: "",
        attIdList: [],
        attDescList: [],
        thumbnail:
          "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg",
      },
      word: "",

      show: true,
    };
  },
  components: {
    KakaoMap,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    addPlanList(reco) {
      this.selected.push({
        index: reco.contentId,
        title: reco.title,
        image: reco.firstImage,
        text: "",
      });
    },
    async recommend() {
      await http
        .get("/attraction/search/" + this.word)
        .then(({ data }) => {
          console.log("?DSFASDF");
          console.log(data.length);

          if (data.length == 0) {
            alert("검색 결과가 없습니다!");
          } else {
            this.recoList = data;
          }
        })
        .catch(() => {
          alert(`검색어 입력`);
        });
    },

    async write() {
      console.log("=======");
      console.log(this.selected);
      console.log(this.recoList);
      console.log("======");
      // form: {
      //   title: "",
      //   attIdList: [],
      //   attDescList: [],
      //   thumbnail: "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg",
      // },

      this.selected.forEach((element) => {
        console.log("select check");
        console.log(element.image);
        this.form.attIdList.push(element.index);
        this.form.attDescList.push(element.text);
        // this.form.attDescList =[]
        // this.form.attDescList =[0]
        // this.form.attDescList =[0,2]
      });
      this.form.thumbnail = this.selected[0].image;

      console.log("title = " + this.form.title);
      console.log("thumbnail = " + this.form.thumbnail);
      console.log("attIdList = " + this.form.attIdList);
      console.log("attDescList = " + this.form.attDescList);

      let map = new Map();
      map.set("title", this.form.title);
      map.set("thumbnail", this.form.thumbnail);
      map.set("attIdList", this.form.attIdList);
      map.set("attDescList", this.form.attDescList);
      map.set("userId", this.userInfo.userId);

      http.post("/boardcon/list", Object.fromEntries(map)).then(({ data }) => {
        console.log(data);
        this.$router.push({ name: "boardlist" });
      });
    },
    movePage() {
      this.$router.push({ name: "boardlist" });
    },
  },
};
</script>
<style scoped>
.img-under {
  height: 100%;
  width: 100%;
}
.VueCarousel-slide {
  height: 272px;
  width: 100px;
}
.card-body {
  padding: 5px;
  width: 100%;
  position: relative;
}
.btn-select {
  position: absolute;
  bottom: 8px;
  right: 5px;
}
.card-img-top {
  height: 160px;
}
.card-title {
  margin-bottom: 5px;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.card-text {
  margin-bottom: 5px;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
article {
  height: 100%;
  margin-left: 5px;
}
#btn-group {
  font-size: 34px;
  text-align: end;
  margin-bottom: 5px;
}
.btn-circle {
  width: 38px;
  height: 38px;
  text-align: center;
  padding: 6px 0px;
  font-size: 15px;
  border-radius: 20px;
  margin-right: 5px;
}
.btn-circle-sm {
  width: 32px;
  height: 32px;
  text-align: center;
  padding: 6px 0px;
  font-size: 18px;
  border-radius: 20px;
  margin-right: 5px;
  float: right;
}
#controller {
  margin-bottom: 5px;
}
img {
  height: 35px;
  float: right;
}
</style>
