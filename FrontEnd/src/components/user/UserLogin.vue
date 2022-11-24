<template>
  <div class="container mb-5" style="min-width: 1100px">
    <b-row style="display: flex; justify-content: center">
      <div class="mt-5">
        <h2>Welcome to Enjoy Trip!!</h2>
      </div>
    </b-row>
    <b-row class="mt-3">
      <b-col></b-col>
      <b-col cols="4">
        <b-form id="login-form">
          <b-form-checkbox
            class="float-right mt-4"
            id="checkbox-saveid"
            v-model="status"
            name="checkbox-saveid"
            value="checked"
            unchecked-value="unchecked"
          >
            Remember
          </b-form-checkbox>
          <b-form-group
            class="mt-4"
            id="input-group-id"
            label="ID(email) :"
            label-for="input-id"
          >
            <b-form-input
              id="input-id"
              v-model="userDto.userId"
              type="email"
              @keyup.enter="confirm"
              placeholder="Enter ID"
              required
            ></b-form-input>
          </b-form-group>

          <b-button
            class="float-right text-danger mt-1"
            id="btn-findpwd"
            @click="show = true"
            >비밀번호 찾기</b-button
          >
          <b-modal v-model="show" title="비밀번호 찾기">
            <!-- Modal body -->
            <div class="modal-body">
              <b-form id="findpwd-form">
                <div class="mb-3">
                  <label for="question" class="form-label"
                    >찾으시는 계정의 아이디를 입력하세요.</label
                  >
                  <input
                    type="email"
                    class="form-control"
                    id="finduserid"
                    v-model="findUserId"
                    placeholder="아이디(이메일)"
                    name="finduserid"
                  />
                </div>
                <div class="mb-3">
                  <label for="question" class="form-label"
                    >이름을 입력하세요.</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    id="findusername"
                    v-model="findUserName"
                    placeholder="이름"
                    name="findusername"
                  />
                </div>
                <div class="mb-3">
                  <p
                    class="float-left"
                    id="findpassword-result"
                    style="font-size: 15px"
                  ></p>
                  <b-button
                    type="button"
                    @click="findPwd"
                    id="btn-find-password"
                    class="btn float-right"
                  >
                    찾기
                  </b-button>
                </div>
              </b-form>
            </div>

            <!-- Modal footer -->
            <template #modal-footer>
              <div class="w-100">
                <b-button
                  class="btn btn-outline-dark float-right"
                  id="btn-finish"
                  @click="finishFindPassword"
                >
                  확인
                </b-button>
              </div>
            </template>
          </b-modal>

          <b-form-group
            class="mt-4"
            id="input-group-pwd"
            label="Password :"
            label-for="input-pwd"
          >
            <b-form-input
              id="input-pwd"
              v-model="userDto.userPwd"
              type="password"
              @keyup.enter="confirm"
              placeholder="Enter Password"
              required
            ></b-form-input>
          </b-form-group>

          <b-alert show variant="danger" v-if="isLoginError"
            >입력정보를 확인하세요.</b-alert
          >
          <div class="mt-3" id="btn-box">
            <b-button type="button" id="btn-login" class="btn" @click="confirm">
              로그인
            </b-button>
            <b-button
              type="button"
              id="btn-mv-register"
              class="btn"
              @click="movePage"
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
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";
export default {
  name: "UserLogin",
  data() {
    return {
      userDto: {
        userId: "",
        userPwd: "",
      },
      status: "unchecked",
      show: false,
      findUserId: "",
      findUserName: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  created() {
    if (this.$cookies.get("save_id")) {
      this.status = "checked";
      this.userDto.userId = this.$cookies.get("save_id");
    }
  },
  methods: {
    ...mapActions(memberStore, ["editUserInfo", "userConfirm", "getUserInfo"]),
    async confirm() {
      let map = new Map();
      map.set("userId", this.userDto.userId);
      map.set("userPwd", this.userDto.userPwd);

      if (this.status == "checked") {
        this.$cookies.remove("save_id");
        this.$cookies.set("save_id", this.userDto.userId);
      } else {
        this.$cookies.remove("save_id");
      }

      console.log(map);
      await this.userConfirm(map);

      if (this.isLogin) {
        await http
          .get("/user/infoprofile/" + this.userDto.userId)
          .then((data) => {
            console.log("data 찍어볼거야 로그인했어 이제 ㅠㅠ");
            console.log(data);
            this.editUserInfo(data.data.userInfo);
          });

        let token = sessionStorage.getItem("access-token");
        console.log("1. confirm() token >> " + token);
        if (this.isLogin) {
          await this.getUserInfo(token);
          console.log("4. confirm() userInfo :: ", this.userInfo);

          this.$router.push({ name: "home" });
        }
      }
    },
    movePage() {
      this.$router.push({ name: "regist" });
    },
    findPwd() {
      if (this.findUserId == "") {
        alert("아이디를 입력하세요.");
      } else if (this.findUserName == "") {
        alert("이름을 입력하세요.");
      }

      let map = new Map();
      map.set("userId", this.findUserId);
      map.set("userName", this.findUserName);

      http.post("/user/find/", Object.fromEntries(map)).then((data) => {
        document.querySelector("#findpassword-result").innerText = data.data;
      });
    },
    finishFindPassword() {
      this.userDto.userId = this.findUserId;
      this.show = false;
    },
  },
};
</script>

<style scope>
.card-img {
  object-fit: cover;
}
#login-form {
  border: 1px solid lightgray;
  border-radius: 7px;
  padding: 20px;
  text-align: left;
}
#btn-find-password {
  color: white;
  background-color: #1e4356;
}
#btn-find-password:hover {
  font-weight: bold;
}
#btn-finish {
  background-color: white;
}
#btn-finish:hover {
  background-color: black;
}
#btn-findpwd {
  margin-bottom: 5px;
  background-color: white;
  font-size: 13px;
}
#btn-findpwd:hover {
  background-color: lightgray;
}
#btn-box {
  text-align: center;
}
#btn-login {
  color: white;
  background-color: #1e4356;
  margin-right: 20px;
}
#btn-mv-register {
  color: white;
  background-color: #1e4356;
  margin-left: 20px;
}
</style>
