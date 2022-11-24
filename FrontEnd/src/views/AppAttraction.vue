<template>
  <div class="container">
    <near-select v-on:reco="reco"></near-select>
    <kakao-map
      :recoList="recoList"
      style="margin: auto; width: 100%"
      class="mb-5"
    ></kakao-map>
  </div>
</template>

<script>
import NearSelect from "@/components/attraction/NearSelect.vue";
import KakaoMap from "@/components/KakaoMap.vue";

export default {
  name: "AppAttraction",
  data() {
    return {
      recoList: [],
    };
  },
  components: {
    NearSelect,
    KakaoMap,
  },
  methods: {
    reco(recoli) {
      console.log("AppAttraction");
      console.log(recoli);
      this.recoList = recoli;
    },
  },
};
</script>
<style></style>
