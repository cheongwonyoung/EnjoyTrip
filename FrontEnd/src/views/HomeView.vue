<template>
  <div class="home">
    <!-- <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/> -->
    <main-image></main-image>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from "@/components/HelloWorld.vue";
import MainImage from "@/components/MainImage.vue";

export default {
  name: "HomeView",
  components: {
    // HelloWorld,
    MainImage,
  },
};
</script>
