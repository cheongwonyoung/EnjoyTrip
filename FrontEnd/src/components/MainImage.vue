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
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
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
