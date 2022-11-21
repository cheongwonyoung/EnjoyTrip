<template>
  <div class="container">
    <div>Home / 여행지 추천</div>
    <div><h2>여행지 추천</h2></div>
    <b-container class="bv-example-row">
      <b-row>
        <b-col>
          <b-form-select
            value-field="areaCode"
            text-field="areaName"
            v-model="selectedArea"
            :options="optionsArea"
            @change="makeGungu"
          >
          </b-form-select>
        </b-col>
        <b-col>
          <b-form-select
            value-field="sigunguCode"
            text-field="sigunguName"
            v-model="selectedGungu"
            :options="optionsGungu"
          ></b-form-select>
        </b-col>
        <b-col
          ><b-button variant="danger" @click="recommend">Go!</b-button></b-col
        >
      </b-row>
      <b-row>
        <b-button-group size="lg">
          <b-button variant="success" @click="selectedEvent = 100">
            전체
          </b-button>
          <b-button variant="info" @click="selectedEvent = 12">관광지</b-button>
          <b-button variant="outline-info" @click="selectedEvent = 14"
            >문화시설</b-button
          >
          <b-button variant="danger" @click="selectedEvent = 15"
            >행사/공연/축제</b-button
          >
          <b-button variant="primary" @click="selectedEvent = 28"
            >레포츠</b-button
          >
          <b-button variant="secondary" @click="selectedEvent = 32">
            숙박
          </b-button>
          <b-button variant="dark" @click="selectedEvent = 38">쇼핑</b-button>
          <b-button variant="warning" @click="selectedEvent = 39"
            >음식점</b-button
          >
        </b-button-group>
      </b-row>
    </b-container>
    <div>
      <b-card-group columns>
        <div v-for="(portfolio, idx) in portfolios" :key="idx">
          <b-card
            v-b-modal="`modal-${idx}`"
            v-bind:title="portfolio.title"
            v-bind:img-src="portfolio.firstImage"
            img-alt="Image"
            img-top
          >
            <!-- <b-card-text> {{ portfolio.overview }} </b-card-text> -->
            <!-- <template #footer>
              <small class="text-muted">Last updated 3 mins ago</small>
            </template> -->
          </b-card>

          <b-modal :id="'modal-' + idx">
            <h1>여행지 정보</h1>
            <b-card
              v-bind:img-src="portfolio.firstImage"
              v-bind:title="portfolio.title"
              img-alt="Image"
              img-top
            >
            </b-card>
            <b-card-text> {{ portfolio.overview }} </b-card-text>
          </b-modal>
        </div>
      </b-card-group>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";

export default {
  data() {
    return {
      portfolios: [],
      selectedEvent: 100,
      selectedArea: null,
      optionsArea: [],
      selectedGungu: null,
      optionsGungu: [],
    };
  },
  watch: {
    selectedEvent: {
      handler() {
        // 관광지
        if (this.selectedEvent == 12) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }
        // 문화시설
        else if (this.selectedEvent == 14) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }
        // 행사/공연/축제
        else if (this.selectedEvent == 15) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }

        // 레포츠
        else if (this.selectedEvent == 28) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }
        // 숙박
        else if (this.selectedEvent == 32) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }
        // 쇼핑
        else if (this.selectedEvent == 38) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }
        // 음식점
        else if (this.selectedEvent == 39) {
          console.log("누른 이벤트 = " + this.selectedEvent);
          this.recommend();
        }
        // 전체 보이기
        else {
          console.log("누른 이벤트 = " + 100);
          http
            .get(
              "/attraction/recommend/" +
                this.selectedArea +
                "/" +
                this.selectedGungu
            )
            .then((data) => {
              // console.log(data.data.filter((dt) => dt.contentTypeId == this.selectedEvent));
              this.portfolios = data.data.filter(
                (dt) => dt.contentTypeId != this.selectedEvent
              );
              console.log(this.portfolios);
            });
        }
      },
    },
  },
  methods: {
    makeGungu() {
      http.get("attraction/gugun/" + this.selectedArea).then(({ data }) => {
        this.optionsGungu = [];
        // this.optionsGungu.push({
        //   sigunguCode: null,
        //   sigunguName: "선택하세요",
        // });
        this.optionsGungu.push(...data);
      });
    },
    recommend() {
      http
        .get(
          "/attraction/recommend/" +
            this.selectedArea +
            "/" +
            this.selectedGungu
        )
        .then((data) => {
          console.log(data);
          console.log(
            data.data.filter((dt) => dt.contentTypeId == this.selectedEvent)
          );
          if (this.selectedEvent == 100) {
            this.portfolios = data.data;
          } else {
            this.portfolios = data.data.filter(
              (dt) => dt.contentTypeId == this.selectedEvent
            );
          }
        });
    },
  },
  created() {
    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다

      //   console.log(this);

      navigator.geolocation.getCurrentPosition((position) => {
        var lat = position.coords.latitude; // 위도
        var lon = position.coords.longitude; // 경도
        console.log(lat);
        console.log(lon);
        http
          .get("/attraction/defaultrecommend/" + lat + "/" + lon)
          .then(({ data }) => {
            console.log("data1=" + data[1]);
            this.selectedGungu = data[0].sigunguCode;
            this.selectedArea = data[0].areaCode;
            this.portfolios.push(...data);
            console.log("default reco = ");
            console.log(this.portfolios);
            console.log(this.selectedGungu);
            console.log(this.selectedArea);
          });
      });
    } else {
      let lon = 37.5012743;
      let lat = 127.039585;
      http
        .get("/attraction/defaultrecommend/" + lat + "/" + lon)
        .then(({ data }) => {
          //   console.log(data);
          this.portfolios.push(...data);
        });
    }
    http.get("attraction/sido").then(({ data }) => {
      // this.optionsArea.push({ areaCode: null, areaName: "선택하세요" });
      this.optionsArea.push(...data);
      // console.log("sido = " + data[0].areaCode);
      // this.selectedArea = data[0].areaCode;
    });
    http.get("attraction/gugun/" + this.selectedArea).then(({ data }) => {
      this.optionsGungu = [];
      // this.optionsGungu.push({
      //   sigunguCode: null,
      //   sigunguName: "선택하세요",
      // });
      this.optionsGungu.push(...data);
    });
  },
};
</script>

<style></style>
