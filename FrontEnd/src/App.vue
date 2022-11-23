<template>
  <div id="app">
    <div id="wrapper">
      <the-header-navbar></the-header-navbar>

      <router-view />
    </div>

    <the-footer></the-footer>
  </div>
</template>
<script>
import TheHeaderNavbar from "@/components/common/TheHeaderNavbar.vue";
import TheFooter from "@/components/common/TheFooter.vue";

export default {
  name: "App",
  components: {
    TheHeaderNavbar,
    TheFooter,
  },
};
</script>
<style>
@font-face {
  font-family: "bitbit";
  src: url("//cdn.df.nexon.com/img/common/font/DNFBitBit-Regular.woff"),
    url("//cdn.df.nexon.com/img/common/font/DNFBitBit-Regular.woff2");
}
#app {
  font-family: "bitbit";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: snow;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: 0px 0px 30px rgba(127, 137, 161, 0.25);
}

nav a.router-link-exact-active {
  text-decoration: underline;
  color: gray;
}

#wrapper {
  height: auto;
  min-height: 100%;
  padding-bottom: (footer높이);
}
</style>
