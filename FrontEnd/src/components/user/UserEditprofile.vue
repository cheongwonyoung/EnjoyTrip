<template>
  <div>
    <b-row>
      <div class="float-left">
        <router-link to="/">Home</router-link> /
        <router-link to="/user/infoprofile">마이페이지</router-link> / 정보 수정
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
            id="input-group-name"
            label="Name :"
            label-for="input-name"
          >
            <b-form-input
              id="input-name"
              v-model="userDto.userName"
              type="text"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-pwd"
            label="Current Password :"
            label-for="input-pwd-edit-before"
          >
            <b-form-input
              id="input-pwd-edit-before"
              v-model="userDto.userPwd"
              type="password"
              placeholder="Enter current Password"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-pwdchange"
            label="Password to Change :"
            label-for="input-pwd-edit-after"
          >
            <b-form-input
              id="input-pwd-edit-after"
              v-model="changePwd"
              type="password"
              placeholder="Enter Password to change"
              required
            ></b-form-input>
          </b-form-group>

          <div class="mt-5" id="btn-box">
            <b-button
              id="btn-toggle"
              :pressed.sync="myToggle"
              variant="primary"
              @click.prevent="togglePassword"
              >비밀번호 보기</b-button
            >
            <b-button
              type="button"
              id="btn-editprofile"
              class="btn"
              @click="editProfile"
              >정보 변경 완료</b-button
            >
          </div>
        </b-form>
      </b-col>

      <b-col></b-col>
    </b-row>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserEditprofile",
  data() {
    return {
      userDto: {
        userName: "",
        userId: "",
        userPwd: "",
      },
      currentPwd: "",
      changePwd: "",
      myToggle: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    http.get("/user/editprofile/" + this.userInfo.userId).then((data) => {
      this.userDto.userName = data.data.userInfo.userName;
      this.userDto.userId = data.data.userInfo.userId;
      this.currentPwd = data.data.userInfo.userPwd;
    });
  },
  methods: {
    togglePassword() {
      let edit_pwd_before = document.querySelector("#input-pwd-edit-before");
      let edit_pwd_after = document.querySelector("#input-pwd-edit-after");
      let btn_toggle_text = document.querySelector("#btn-toggle");
      if (this.myToggle) {
        // 비밀번호 보기 버튼이 눌린다면
        edit_pwd_before.setAttribute("type", "text");
        edit_pwd_after.setAttribute("type", "text");
        btn_toggle_text.innerText = "비밀번호 가리기";
      } else {
        // 비밀번호 가리기 버튼이눌린다면
        edit_pwd_before.setAttribute("type", "password");
        edit_pwd_after.setAttribute("type", "password");
        btn_toggle_text.innerText = "비밀번호 보이기";
      }
    },
    editProfile() {
      if (this.userDto.userPwd == "") {
        alert("현재 패스워드를 반드시 입력하세요.");
        return;
      }
      if (this.userDto.userPwd != this.currentPwd) {
        console.log(this.userDto.userPwd);
        console.log(this.currentPwd);
        alert("비밀번호가 일치하지 않습니다.");
        return;
      } else {
        if (this.changePwd != "") {
          this.userDto.userPwd = this.changePwd;
        }
      }

      http.put("/user/edit", JSON.stringify(this.userDto)).then(({ data }) => {
        console.log(data);
        this.$router.push({ name: "infoprofile" });
      });
    },
  },
};
</script>

<style>
#btn-box {
  text-align: center;
}
#btn-toggle {
  color: #1e4356;
  background-color: #ffffff;
  border: 1px solid #1e4356;
  margin-right: 20px;
}
#btn-toggle:hover {
  font-weight: bold;
}
#btn-editprofile {
  color: white;
  background-color: #1e4356;
  margin-left: 20px;
}
#btn-editprofile:hover {
  font-weight: bold;
}
</style>
