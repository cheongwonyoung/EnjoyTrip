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
        :img-src="
          require('../assets/img/background/back_' + (idx + 1) + '.jpg')
        "
      ></b-carousel-slide>
    </b-carousel>

    <div id="text" class="mt-5">
      <h1
        class="mb-1 text-light"
        style="
          @font-face {
            font-family: 'GmarketSansMedium';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
              format('woff');
            font-weight: normal;
            font-style: normal;
          }
        "
      >
        ENJOY TRIP
      </h1>
      <h5
        class="mt-5 mb-5 text-light"
        style="
          font-size: 35px;
          text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;
          text-shadow: 1px 1px 1px #000;
        "
      >
        <em
          >진정한 여행은 새로운 풍경을 보러 가는 것이 아니라, <br />세상을
          바라보는 또 하나의 눈을 얻어오는 것이다.</em
        >
      </h5>
    </div>

    <!-- 하단에 보이는 여행지들 -->
    <!-- <div v-for="(item, idx) in contentAll" :key="idx">
      <b-card title="Card Title" :img-src="item.img" img-alt="Image" img-top tag="article" style="max-width: 20rem" class="mb-2">
        <b-button v-b-modal="`modal-${idx}`" variant="primary">{{ item.title }}</b-button>
      </b-card>
      <b-modal :id="'modal-' + idx">
        <h1>여행지 정보</h1>
        <b-card v-bind:img-src="item.img" v-bind:title="item.title" img-alt="Image" img-top> </b-card>
        <b-card-text> {{ item.overview }} </b-card-text>
      </b-modal>
    </div> -->

    <div class="carousel-title-container bv-example-row mt-5">
      <b-row>
        <b-col style="position: relative; padding: 0px">
          <b-alert show
            ><h2 style="padding-top: 5px">
              🔍 내 주변엔 뭐가 있을까? 🔍
            </h2></b-alert
          >
        </b-col>
      </b-row>
    </div>

    <carousel
      :perPage="4"
      :navigationEnabled="true"
      :paginationEnabled="false"
      class="carousel-container mt-2 mb-5"
    >
      <slide v-for="(reco, index) in contentAll" :key="index">
        <b-card
          :img-src="reco.img"
          img-alt="Image"
          img-width="400"
          img-height="300"
          img-top
          tag="article"
          style="max-width: 20rem"
          class="mb-2"
        >
          <b-button
            v-b-modal="`modal-${index}`"
            variant="dark"
            style="font-size: 20px"
            @click="$bvModal.show(`modal-${index}`)"
            >{{ reco.title }}</b-button
          >
        </b-card>
        <b-modal :id="'modal-' + index" hide-footer title="이 여행지는요">
          <b-card
            v-bind:img-src="reco.img"
            v-bind:title="reco.title"
            img-alt="Image"
            img-width="400"
            img-height="300"
            img-top
            class="mb-2"
          >
          </b-card>
          <b-card-text v-if="reco.addr1" style="margin: 5px">
            주소 : {{ reco.addr1 }}
          </b-card-text>
          <b-card-text v-if="reco.addr2" style="margin: 5px">
            상세주소 : {{ reco.addr2 }}
          </b-card-text>
          <b-card-text v-if="reco.tel" style="margin: 5px">
            전화번호 : {{ reco.tel }}
          </b-card-text>
          <b-button class="mt-3" block @click="$bvModal.hide(`modal-${index}`)"
            >확인</b-button
          >
        </b-modal>
      </slide>
    </carousel>
  </div>
</template>

<script>
import http from "@/api/http.js";
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
      buttonBox: [],
      contentAll: [],
      show: false,
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
  created() {
    if (navigator.geolocation) {
      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition((position) => {
        var lat = position.coords.latitude; // 위도
        var lon = position.coords.longitude; // 경도
        console.log(lat);
        console.log(lon);

        http
          .get("/attraction/defaultrecommend/" + lat + "/" + lon)
          .then(({ data }) => {
            data.forEach((place) => {
              // console.log("??");
              // console.log(place.title);

              // if (key >= 4) {
              //   return false;
              // }
              this.buttonBox.push(place.title);
              // console.log(this.buttonBox);
              var contentImg;
              if (place.firstImage == "") {
                contentImg =
                  "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg";
              } else {
                contentImg = place.firstImage;
              }

              this.contentAll.push({
                img: contentImg,
                title: place.title,
                addr1: place.addr1,
                addr2: place.addr2,
                tel: place.tel,
              });
            });
          });
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
            data.forEach((place) => {
              console.log("??");
              console.log(place.title);

              // if (key >= 4) {
              //   return false;
              // }
              this.buttonBox.push(place.title);
              console.log(this.buttonBox);
              var contentImg;
              if (place.firstImage == "") {
                contentImg =
                  "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg";
              } else {
                contentImg = place.firstImage;
              }

              this.contentAll.push({
                img: contentImg,
                title: place.title,
                overview: place.overview,
              });
            });
          });
      });
    }
  },
};
</script>

<style>
#text {
  width: 1350px;
  height: 450px;
  /* border: 1px solid gray; /
  / background-color: aquamarine; */
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

.carousel-container {
  margin: auto;
  max-width: 1300px;
}
.carousel-title-container {
  margin: auto;
  max-width: 500px;
}
.card-body {
  margin: auto;
  height: 100px;
}
.modal-footer button {
  color: #1e4356;
  background-color: #ffffff;
  border: 1px solid #1e4356;
  margin-left: 20px;
}
.modal-footer button:hover {
  font-weight: bold;
  color: white;
  background-color: #1e4356;
}
</style>
