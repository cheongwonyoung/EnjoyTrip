<template>
  <div id="select-box" class="mt-5 mb-4">
    <div class="mb-3"><h2>여행지 주변시설</h2></div>
    <div class="bv-example-row">
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
          ><b-form-select
            :options="optionsEvent"
            v-model="selectedEvent"
          ></b-form-select
        ></b-col>
        <b-col
          ><b-button style="width: 100%" variant="dark" @click="recommend"
            >Go!</b-button
          ></b-col
        >
      </b-row>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";

export default {
  data() {
    return {
      selectedArea: null,
      optionsArea: [],
      selectedGungu: null,
      optionsGungu: [],
      selectedEvent: 0,
      optionsEvent: [
        { value: 0, text: "선택하세요" },
        { value: "32", text: "숙박" },
        { value: "15", text: "이벤트" },
      ],
      recommendList: [],
    };
  },
  methods: {
    makeGungu() {
      http.get("attraction/gugun/" + this.selectedArea).then(({ data }) => {
        this.optionsGungu = [];
        this.optionsGungu.push({
          sigunguCode: null,
          sigunguName: "구/군 선택",
        });
        this.optionsGungu.push(...data);
      });
    },
    async recommend() {
      console.log(this.selectedEvent);
      await http
        .get(
          "/attraction/recommend/" +
            this.selectedArea +
            "/" +
            this.selectedGungu +
            "/" +
            this.selectedEvent
        )
        .then(({ data }) => {
          console.log("here");
          this.recommendList = data;
        });
      this.$emit("reco", this.recommendList);
    },
  },
  created() {
    http.get("attraction/sido").then(({ data }) => {
      console.log(data);
      this.optionsArea.push({ areaCode: null, areaName: "시/도 선택" });
      this.optionsArea.push(...data);
    });
  },
};
</script>

<style>
#select-box {
  width: 100%;
  margin: auto;
}
</style>
