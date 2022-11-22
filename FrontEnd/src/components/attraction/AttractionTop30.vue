<template>
  <div class="container mt-5 mb-5">
    <div class="mb-3"><h2>여행지 추천</h2></div>
    <b-container class="bv-example-row">
      <div class="outdiv">
        <b-row id="frow">
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
              @change="recommend"
            ></b-form-select>
          </b-col>
        </b-row>
      </div>
      <div class="outdiv">
        <b-row>
          <b-button class="btn-grp" @click="selectedEvent = 100">
            전체
          </b-button>
          <b-button class="btn-grp" @click="selectedEvent = 12"
            >관광지</b-button
          >
          <b-button class="btn-grp" @click="selectedEvent = 14"
            >문화시설</b-button
          >
          <b-button class="btn-grp" @click="selectedEvent = 15"
            >행사/공연/축제</b-button
          >
          <b-button class="btn-grp" @click="selectedEvent = 28"
            >레포츠</b-button
          >
          <b-button class="btn-grp" @click="selectedEvent = 32">
            숙박
          </b-button>
          <b-button class="btn-grp" @click="selectedEvent = 38">쇼핑</b-button>
          <b-button class="btn-grp" @click="selectedEvent = 39"
            >음식점</b-button
          >
        </b-row>
      </div>
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
          this.recommend();
        }
      },
    },
  },
  methods: {
    makeGungu() {
      http.get("attraction/gugun/" + this.selectedArea).then(({ data }) => {
        this.optionsGungu = [];
        this.optionsGungu.push(...data);
        this.optionsGungu = [];
        this.optionsGungu.push("구/군 선택");
        this.optionsGungu.push(...data);
        this.selectedGungu = this.optionsGungu[0];
      });
    },
    goMethod() {},
    recommend() {
      console.log("==========");
      console.log(this.selectedArea);
      console.log("==========");
      if (this.selectedArea > 0) {
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
      } else {
        navigator.geolocation.getCurrentPosition((position) => {
          var lat = position.coords.latitude; // 위도
          var lon = position.coords.longitude; // 경도
          console.log("화인");
          console.log(lat);
          console.log(lon);
          http
            .get("/attraction/defaultrecommend/" + lat + "/" + lon)
            .then(({ data }) => {
              console.log(data);
              console.log(this.selectedEvent);
              if (this.selectedEvent == 100) {
                this.portfolios = data;
              } else {
                this.portfolios = data.filter(
                  (dt) => dt.contentTypeId == this.selectedEvent
                );
              }
            });
        });
      }
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
            // this.selectedArea = data[0].areaCode;

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
      this.optionsArea = [];
      this.optionsArea.push("시/도 선택");
      this.optionsArea.push(...data);
      this.selectedArea = this.optionsArea[0];
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

<style>
.btn-grp {
  margin: 0px 25px;
  margin-top: 30px;
  margin-bottom: 30px;
  color: black;
  background-color: white;
  box-shadow: 5px 2px 2px gray;
}
.btn-grp:hover {
  color: white;
  background-color: black;
}
.outdiv {
  display: flex;
  justify-content: center;
}
#frow {
  width: 50%;
}
</style>
