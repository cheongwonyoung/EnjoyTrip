<template>
  <div id="main">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="2000"
      controls
      indicators
      background="#ababab"
      img-width="20800"
      img-height=""
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Text slides with image -->
      <b-carousel-slide
        img-height="100"
        v-for="idx in 18"
        :key="idx"
        caption="Next Page"
        text="Click below button for other pages"
        :img-src="
          require('../assets/img/background/back_' + (idx + 1) + '.jpg')
        "
      ></b-carousel-slide>
    </b-carousel>

    <div id="text">
      <h1
        class="mb-1 text-light"
        style="
          font-size: 70px;
          text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;
          text-shadow: 1px 1px 1px #000;
        "
      >
        ENJOY TRIP
      </h1>
      <h5
        class="mb-5 text-light"
        style="
          font-size: 35px;
          text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;
          text-shadow: 1px 1px 1px #000;
        "
      >
        <em
          >진정한 여행은 새로운 풍경을 보러 가는 것이 아니라, 세상을 바라보는 또
          하나의 눈을 얻어오는 것이다.</em
        >
      </h5>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
    };
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
            console.log(data);
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
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>

<style>
#text {
  width: 1350px;
  height: 450px;
  /* border: 1px solid gray; */
  /* background-color: aquamarine; */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.carousel-inner {
  height: 800px;
}
.carousel-item {
  height: 100%;
}
</style>
