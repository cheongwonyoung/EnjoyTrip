<template>
  <div id="header-container">
    <b-navbar toggleable="lg" type="dark">
      <b-row style="margin: auto; width: 80%">
        <b-col cols="2">
          <b-navbar-brand
            ><a href="/"
              ><img
                :src="require('../../assets/img/logo_color.png')"
                id="img-logo" /></a
          ></b-navbar-brand>
          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        </b-col>

        <b-col cols="10">
          <b-collapse id="nav-collapse" is-nav class="float-right">
            <!-- After login -->
            <b-navbar-nav class="float-right" v-if="userInfo">
              <a class="align-self-center" id="login-user">
                <b-avatar
                  icon="people-fill"
                  style="margin-right: 5px"
                ></b-avatar>
                {{ userInfo.userName }}님 환영합니다.
              </a>
              <a
                href="#"
                class="align-self-center link"
                @click.prevent="onClickLogout"
                ><img
                  :src="require('../../assets/img/logout.png')"
                  class="img-navbar"
                />로그아웃</a
              >
              <router-link
                :to="{ name: 'infoprofile' }"
                class="link align-self-center"
                ><img
                  :src="require('../../assets/img/info.png')"
                  class="img-navbar"
                />마이페이지</router-link
              >
              <b-nav-item class="align-self-center">
                <router-link :to="{ name: 'boardlist' }"
                  ><img
                    :src="require('../../assets/img/plan.png')"
                    class="img-navbar"
                  />일정 공유</router-link
                >

                <router-link :to="{ name: 'attractiontop30' }"
                  ><img
                    :src="require('../../assets/img/camping.png')"
                    class="img-navbar"
                  />여행지추천</router-link
                >
                <router-link :to="{ name: 'attraction' }"
                  ><img
                    :src="require('../../assets/img/accomodation.png')"
                    class="img-navbar"
                  />주변시설</router-link
                >
                <i class="bi bi-list mobile-nav-toggle"></i>
              </b-nav-item>
            </b-navbar-nav>

            <!-- Before login -->
            <b-navbar-nav v-else>
              <b-nav-item class="align-self-center">
                <router-link :to="{ name: 'login' }"
                  ><img
                    :src="require('../../assets/img/login.png')"
                    class="img-navbar"
                  />로그인</router-link
                >
                <router-link :to="{ name: 'regist' }"
                  ><img
                    :src="require('../../assets/img/signup.png')"
                    class="img-navbar"
                  />회원가입</router-link
                >
                <router-link :to="{ name: 'boardlist' }"
                  ><img
                    :src="require('../../assets/img/plan.png')"
                    class="img-navbar"
                  />일정 공유</router-link
                >

                <router-link :to="{ name: 'attractiontop30' }"
                  ><img
                    :src="require('../../assets/img/camping.png')"
                    class="img-navbar"
                  />여행지 추천</router-link
                >
                <router-link :to="{ name: 'attraction' }"
                  ><img
                    :src="require('../../assets/img/accomodation.png')"
                    class="img-navbar"
                  />여행지 주변시설</router-link
                >
                <i class="bi bi-list mobile-nav-toggle"></i>
              </b-nav-item>
            </b-navbar-nav>
          </b-collapse>
        </b-col>
      </b-row>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "TheHeaderNavbar",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.userid);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      this.$router.push({ name: "login" });
    },
  },
};
</script>

<style>
#header-container {
  box-shadow: 3px 3px 3px lightgray;
  position: sticky;
  top: 0;
  z-index: 1;
  background-color: white;
}

#img-logo {
  width: 70px;
}

.img-navbar {
  width: 20px;
  margin-right: 2px;
}

#headname {
  margin-left: 300px;
}

.navbar {
  padding: 0;
}

.navbar ul {
  margin: 0;
  padding: 0;
  display: flex;
  list-style: none;
  align-items: center;
}

.navbar li {
  position: relative;
}

.navbar a,
.navbar a:focus {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 0 10px 30px;
  font-family: "Open Sans", sans-serif;
  font-size: 14px;
  color: black;
  white-space: nowrap;
  transition: 0.3s;
}

.navbar a i,
.navbar a:focus i {
  font-size: 12px;
  line-height: 0;
  margin-left: 5px;
}

.navbar a:hover,
.navbar .active,
.navbar .active:focus,
.navbar li:hover > a {
  color: gray;
}

#login-user {
  color: black;
}
#login-user:hover {
  text-decoration: unset;
  color: black;
}
</style>
