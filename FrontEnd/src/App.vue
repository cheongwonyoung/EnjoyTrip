<template>
  <div id="app">
    <!-- <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </nav> -->
    <the-header-navbar></the-header-navbar>

    <router-view />
  </div>
</template>
<script>
import TheHeaderNavbar from "@/components/common/TheHeaderNavbar.vue";

export default {
  name: "App",
  components: {
    TheHeaderNavbar,
  },
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
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
  color: #42b983;
}
</style>
