<template>
  <div class="mb-5">
    <b-row class="mt-5">
      <b-col></b-col>

      <b-col cols="4">
        <b-form id="register-form">
          <b-row>
            <b-col></b-col>
            <b-col cols="6" style="text-align: center" class="profile-user-img">
              <img :src="preview" class="profile-user-img" />
            </b-col>
            <b-col></b-col>
          </b-row>

          <b-input-group class="mt-4">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-envelope"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >ID(email)</b-input-group-prepend
            >
            <b-form-input
              id="input-id"
              v-model="userDto.userId"
              type="email"
              disabled="disabled"
              required
            ></b-form-input>
          </b-input-group>

          <b-input-group class="mt-5">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-key"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >Password</b-input-group-prepend
            >
            <b-form-input
              id="input-pwd"
              v-model="userDto.userPwd"
              type="password"
              disabled="disabled"
              required
            ></b-form-input>
          </b-input-group>

          <b-input-group class="mt-5">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-person"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >UserName</b-input-group-prepend
            >
            <b-form-input
              id="input-name"
              v-model="userDto.userName"
              type="text"
              disabled="disabled"
              required
            ></b-form-input>
          </b-input-group>

          <b-input-group class="mt-5">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-check"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >Join Date</b-input-group-prepend
            >
            <b-form-input
              id="input-joinDate"
              v-model="userDto.joinDate"
              type="text"
              disabled="disabled"
              required
            ></b-form-input>
          </b-input-group>

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
                    id="btn-signout"
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
import { mapState, mapGetters, mapActions } from "vuex";

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
        profileImg: "",
        saveFolder: "",
      },
      preview: "",
      checkPwd: "",
      show: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  created() {
    http.get("/user/infoprofile/" + this.userInfo.userId).then((data) => {
      this.userDto = data.data.userInfo;
      console.log(this.userDto);

      // 초기 설정
      if (this.userDto.profileImg) {
        this.preview =
          "http://localhost:80/upload/file/" +
          this.userDto.saveFolder +
          "/" +
          this.userDto.profileImg;
      } else {
        this.preview = require("../../assets/img/user.png");
      }
    });
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    moveEditprofile() {
      this.$router.push({ name: "editprofile" });
    },
    signoutUser() {
      if (this.checkPwd != this.userDto.userPwd) {
        console.log(this.userDto.userPwd);
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

<style scope>
#img-navbar {
  width: 100%;
  height: 80px;
}
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
  color: white;
  background-color: #1e4356;
}

.profile-user-img {
  width: 90px;
  height: 90px;
  border-radius: 100%;
  overflow: hidden;
}
</style>
