<template>
  <div>
    <div><router-link :to="{ name: 'home' }">Home</router-link> / 여행지 일정 공유</div>
    <div><h2>여행지 일정 공유</h2></div>

    <b-form v-if="show">
      <b-form-group id="input-group-1" label="제목" label-for="input-1">
        <b-form-input id="input-1" v-model="form.title" type="name" placeholder="제목 입력" required></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="검색" label-for="input-2">
        <b-form-input id="input-2" v-model="word" placeholder="검색어 입력" required></b-form-input>
        <b-button variant="primary" @click="recommend">검색</b-button>
      </b-form-group>

      <kakao-map :recoList="recoList"></kakao-map>
    </b-form>

    <div>
      <b-form-group v-slot="{ ariaDescribedby }">
        <b-form-checkbox-group id="checkbox-group-2" v-model="selected" :aria-describedby="ariaDescribedby" name="flavour-2">
          <b-card-group>
            <div v-for="(reco, idx) in recoList" :key="idx">
              <b-card width="20px" v-bind:title="reco.title" v-bind:img-src="reco.firstImage" img-alt="Image" img-top>
                <b-card-text> {{ reco.addr1 }} </b-card-text>
                <b-card-text> {{ reco.tel }} </b-card-text>
                <!-- <template #footer>
              <small class="text-muted">Last updated 3 mins ago</small>
            </template> -->
                <b-form-checkbox :value="{ index: reco.contentId, title: reco.title, image: reco.firstImage }"
                  >{{ reco.title }} 추가하기</b-form-checkbox
                >
              </b-card>
            </div>
          </b-card-group>
        </b-form-checkbox-group>
      </b-form-group>
    </div>

    <b-container fluid>
      <b-row class="my-1" v-for="(select, idx) in selected" :key="idx">
        <b-col sm="3">
          <div>
            <b-img v-bind:src="select.image" fluid alt="Responsive image"></b-img>
          </div>
        </b-col>
        <b-col sm="9">
          <label for="input-none">{{ select.title }}</label>
          <b-form-input id="input-none" :state="null" placeholder="글 작성"></b-form-input>
        </b-col>
      </b-row>
    </b-container>
    <b-button variant="primary" @click="write">등록</b-button>
  </div>
</template>

<script>
import KakaoMap from "../KakaoMap.vue";
import http from "@/api/http";

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
        thumbnail: "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg",
      },
      word: "",

      show: true,
    };
  },
  components: {
    KakaoMap,
  },

  methods: {
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
        this.form.attDescList.push(element.title);
      });
      this.form.thumbnail = this.selected[0].image;

      console.log("title = " + this.form.title);
      console.log("thumbnail = " + this.form.thumbnail);
      console.log("attIdList = " + this.form.attIdList);
      console.log("attDescList = " + this.form.attDescList);

      http.post("/boardcon/list", JSON.stringify(this.form)).then(({ data }) => {
        console.log(data);
        this.$router.push({ name: "boardlist" });
      });
    },
  },
};
</script>

<style></style>
