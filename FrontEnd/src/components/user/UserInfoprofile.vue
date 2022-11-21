<template>
  <div>
    <b-row>
      <div class="float-left">
        <router-link to="/">Home</router-link> / 마이페이지
      </div>
    </b-row>
    <b-row class="mt-5">
      <b-col></b-col>

      <b-col cols="6">
        <b-form id="register-form">
          <b-form-group
            class="mt-3"
            id="input-group-id"
            label="ID(email) :"
            label-for="input-id"
          >
            <b-form-input
              id="input-id"
              v-model="userDto.userId"
              type="email"
              disabled="disabled"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-pwd"
            label="Password :"
            label-for="input-pwd"
          >
            <b-form-input
              id="input-pwd"
              v-model="userDto.userPwd"
              type="password"
              disabled="disabled"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-name"
            label="Name :"
            label-for="input-name"
          >
            <b-form-input
              id="input-name"
              v-model="userDto.userName"
              type="text"
              disabled="disabled"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-joinDate"
            label="Join Date :"
            label-for="input-joinDate"
          >
            <b-form-input
              id="input-joinDate"
              v-model="userDto.joinDate"
              type="text"
              disabled="disabled"
              required
            ></b-form-input>
          </b-form-group>
          <div class="mt-5" id="btn-box">
            <b-button
              type="button"
              id="btn-mveditprofile"
              @click="moveEditprofile"
              class="btn"
            >
              정보 수정
            </b-button>
            <b-button
              type="button"
              id="btn-signout"
              class="btn"
              @click="show = true"
              >회원 탈퇴</b-button
            >

            <b-modal v-model="show" title="회원 탈퇴">
              <!-- Modal body -->
              <div class="modal-body">
                <b-form id="findpwd-form">
                  <div class="mb-3">
                    <label for="question" class="form-label"
                      >정말로 탈퇴하시겠습니까?</label
                    >
                    <input
                      type="password"
                      class="form-control"
                      id="signout"
                      v-model="checkPwd"
                      placeholder="현재 비밀번호"
                      name="signout"
                    />
                  </div>
                </b-form>
              </div>

              <!-- Modal footer -->
              <template #modal-footer>
                <div class="w-100">
                  <b-button
                    class="btn btn-outline-dark float-right"
                    @click="signoutUser"
                  >
                    탈퇴
                  </b-button>
                </div>
              </template>
            </b-modal>
          </div>
        </b-form>
      </b-col>

      <b-col></b-col>
    </b-row>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserInfoprofile",
  data() {
    return {
      userDto: {
        userName: "",
        userId: "",
        userPwd: "",
        joinDate: "",
      },
      checkPwd: "",
      show: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    http.get("/user/infoprofile/" + this.userInfo.userId).then((data) => {
      this.userDto = data.data.userInfo;
    });
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    moveEditprofile() {
      this.$router.push({ name: "editprofile" });
    },
    signoutUser() {
      if (this.checkPwd != this.userInfo.userPwd) {
        alert("현재 비밀번호가 틀렸습니다.");
        return;
      } else {
        alert("회원 탈퇴를 진행합니다.");

        console.log(this.userInfo.userid);
        //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
        //+ state에 isLogin, userInfo 정보 변경)
        // this.$store.dispatch("userLogout", this.userInfo.userid);
        this.userLogout(this.userInfo.userid);
        sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
        sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
        if (this.$route.path != "/") this.$router.push({ name: "home" });
        http
          .delete("/user/delete/" + this.userInfo.userId)
          .then((data) => console.log("삭제 완료 : " + data));

        this.show = false;
      }
    },
  },
};
</script>

<style>
#register-form {
  border: 1px solid lightgray;
  border-radius: 7px;
  padding: 20px;
  text-align: left;
}
#btn-box {
  text-align: center;
}
#btn-mveditprofile {
  color: white;
  background-color: #1e4356;
  margin-right: 20px;
}
#btn-mveditprofile:hover {
  font-weight: bold;
}
#btn-signout {
  color: #1e4356;
  background-color: #ffffff;
  border: 1px solid #1e4356;
  margin-left: 20px;
}
#btn-signout:hover {
  font-weight: bold;
}
</style>
