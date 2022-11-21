import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import vueCookies from "vue-cookies";

// Argon 입니답
// import Argon from '@/plugins/argon-kit'
// Vue.use(Argon);

// bootstrap입ㄴ디ㅏ
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

// Import Bootstrap and BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

// 쿠키 사용
Vue.use(vueCookies);
// 필요할 경우 쿠키 만료기간 세팅
Vue.$cookies.config("7d");

// css입니다
// import login from '@/assets/css/login.css'
// import style from '@/assets/css/style.css'
// Vue.use(login)
// Vue.use(style)

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
