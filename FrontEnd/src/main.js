import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import vueCookies from "vue-cookies";

	
//carousel
import VueCarousel from "vue-carousel";
Vue.use(VueCarousel);

// Argon 입니답
// import Argon from '@/plugins/argon-kit'
// Vue.use(Argon);

// bootstrap입ㄴ디ㅏ
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

// Import Bootstrap and BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faUser, faAddressCard } from "@fortawesome/free-regular-svg-icons";
import { faInstagram } from "@fortawesome/free-brands-svg-icons";
import {
  faBed,
  faUmbrellaBeach,
  faUtensils,
  faBuildingColumns,
  faStore,
  faPersonHiking,
  faMapPin,
  faPlaneDeparture,
  faStopwatch,
  faClock,
  faCalendar,
  faArrowUp,
  faMapLocationDot,
  faHeart,
  faEye,
  faShareNodes,
  faCircleInfo,
  faCalendarPlus,
  faHeartCirclePlus,
  faRightLong,
  faPlus,
  faInfo,
  faMinus,
  faSignature,
  faPerson,
  faEnvelope,
  faKey,
  faCheck,
} from "@fortawesome/free-solid-svg-icons";
library.add(
  faBed,
  faUmbrellaBeach,
  faUtensils,
  faBuildingColumns,
  faStore,
  faPersonHiking,
  faMapPin,
  faInstagram,
  faPlaneDeparture,
  faStopwatch,
  faClock,
  faCalendar,
  faArrowUp,
  faMapLocationDot,
  faHeart,
  faEye,
  faUser,
  faShareNodes,
  faCalendarPlus,
  faCircleInfo,
  faHeartCirclePlus,
  faRightLong,
  faPlus,
  faInfo,
  faMinus,
  faSignature,
  faPerson,
  faEnvelope,
  faKey,
  faCheck,
  faAddressCard
);

Vue.component("font-awesome-icon", FontAwesomeIcon);

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
