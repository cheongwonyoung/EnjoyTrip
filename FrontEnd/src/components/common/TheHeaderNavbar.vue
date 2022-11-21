<template>
  <div>
    <b-navbar toggleable="lg" type="dark">
      <b-row style="margin: auto; width: 70%">
        <b-col cols="2">
          <b-navbar-brand href="/"><h3>Enjoy Trip</h3></b-navbar-brand>
          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        </b-col>

        <b-col cols="7">
          <b-collapse id="nav-collapse" is-nav class="float-right">
            <!-- After login -->
            <b-navbar-nav style="margin-left: 400px" v-if="userInfo">
              <b-nav-item class="align-self-center">
                <b-avatar variant="dark" icon="people-fill"></b-avatar>
                {{ userInfo.userName }}({{ userInfo.userId }})님 환영합니다.
              </b-nav-item>
              <b-nav-item
                class="align-self-center link"
                @click.prevent="onClickLogout"
                >로그아웃</b-nav-item
              >
              <b-nav-item class="align-self-center">
                <router-link
                  :to="{ name: 'infoprofile' }"
                  class="link align-self-center"
                  >마이페이지</router-link
                >
              </b-nav-item>
            </b-navbar-nav>

            <!-- Before login -->
            <b-navbar-nav style="margin-left: 400px" v-else>
              <b-nav-item class="align-self-center">
                <router-link :to="{ name: 'login' }">로그인</router-link>
              </b-nav-item>
              <b-nav-item class="align-self-center">
                <router-link :to="{ name: 'regist' }">회원가입</router-link>
              </b-nav-item>
              <i class="bi bi-list mobile-nav-toggle"></i>
            </b-navbar-nav>
          </b-collapse>
        </b-col>
        <b-col cols="3">
          <b-collapse id="nav-collapse" is-nav class="float-right">
            <!-- <b-navbar-nav>
          <b-nav-item href="#">Link</b-nav-item>
          <b-nav-item href="#" disabled>Disabled</b-nav-item>
        </b-navbar-nav> -->
            <b-navbar-nav>
              <b-nav-item href="#">
                <router-link :to="{ name: 'boardlist' }">일정 공유</router-link>

                <router-link :to="{ name: 'attractiontop30' }"
                  >여행지 추천</router-link
                >
                <router-link :to="{ name: 'attraction' }"
                  >여행지 주변시설</router-link
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
#headname {
  margin-left: 300px;
}

.navbar {
  padding: 0;
  background: #1e4356;
  opacity: 0.8;
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
  color: #fff;
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
  color: #a2cce3;
}
</style>
