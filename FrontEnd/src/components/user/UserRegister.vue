<template>
  <div>
    <b-row>
      <div class="float-left">
        <router-link to="/">Home</router-link> / 회원가입
      </div>
    </b-row>
    <b-row class="mt-5">
      <b-col></b-col>

      <b-col cols="6">
        <b-form id="register-form">
          <b-form-group
            class="mt-3"
            id="input-group-name"
            label="Name :"
            label-for="input-name"
          >
            <b-form-input
              id="input-name"
              v-model="userDto.userName"
              type="text"
              placeholder="Enter Name"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-id"
            label="ID(email) :"
            label-for="input-id"
          >
            <div
              class="float-end"
              id="idcheck-result"
              style="font-size: 15px"
            ></div>
            <b-form-input
              id="input-id"
              v-model="userDto.userId"
              type="email"
              placeholder="Enter ID"
              required
              @input="idChecker"
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
              placeholder="Enter Password"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group
            class="mt-5"
            id="input-group-pwdcheck"
            label="Check Password :"
            label-for="input-pwdcheck"
          >
            <b-form-input
              id="input-pwdcheck"
              v-model="userpwd2"
              type="password"
              placeholder="Enter Password Again"
              required
            ></b-form-input>
          </b-form-group>
          <div class="mt-5" id="btn-box">
            <b-button
              type="button"
              id="btn-register"
              class="btn"
              @click="registUser"
              >회원가입</b-button
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

export default {
  name: "UserRegister",
  data() {
    return {
      userDto: {
        userName: "",
        userId: "",
        userPwd: "",
      },
      userpwd2: "",
    };
  },
  methods: {
    idChecker() {
      let userid = this.userDto.userId;
      let resultDiv = document.querySelector("#idcheck-result");
      if (!userid.includes("@")) {
        resultDiv.setAttribute("class", "text-dark float-end");
        resultDiv.setAttribute("style", "font-size:15px;display:inline-block");
        resultDiv.textContent = "아이디는 '@'를 포함한 Email 형식입니다.";
      } else {
        console.log(userid); /* 
			fetch("${root}/user?act=idcheck&userid=" + userid) */
        http.get("/user/" + userid).then((data) => {
          if (data.data == 0) {
            console.log("data는 0입니다!!");
            resultDiv.setAttribute("class", "text-primary float-end");
            resultDiv.setAttribute(
              "style",
              "font-size:15px;display:inline-block"
            );
            resultDiv.textContent = userid + "는 사용할 수 있습니다.";
          } else {
            console.log("data는 1입니다!!");
            resultDiv.setAttribute("class", "text-danger float-end");
            resultDiv.setAttribute(
              "style",
              "font-size:15px;display:inline-block"
            );
            resultDiv.textContent = userid + "는 이미 사용중입니다.";
          }
        });
      }
    },
    registUser() {
      if (this.userDto.userName == "") {
        alert("이름을 입력하세요.");
        return;
      } else if (this.userDto.userId == "") {
        alert("아이디(email)를 입력하세요.");
        return;
      } else if (this.userDto.userPwd == "") {
        alert("패스워드를 입력하세요.");
        return;
      } else if (this.userpwd2 == "") {
        alert("패스워드를 확인하세요!");
        return;
      }
      if (this.userDto.userPwd != this.userpwd2) {
        console.log(this.userDto.userPwd);
        console.log(this.userpwd2);
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }

      http
        .post("/user/regist", JSON.stringify(this.userDto))
        .then(({ data }) => {
          console.log(data);
          this.$router.push({ name: "login" });
        });

      alert(JSON.stringify(this.userDto));
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
#btn-register {
  color: white;
  background-color: #1e4356;
  margin-right: 20px;
}
#btn-register:hover {
  font-weight: bold;
}
</style>
